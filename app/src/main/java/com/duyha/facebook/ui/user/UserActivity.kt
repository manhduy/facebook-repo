package com.duyha.facebook.ui.user

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.duyha.facebook.R
import com.duyha.facebook.data.model.User
import com.duyha.facebook.databinding.ActivityUserBinding
import com.duyha.facebook.ui.base.BaseActivity
import com.duyha.facebook.ui.base.ErrorEvent
import com.duyha.facebook.ui.user.adapter.LoadStateAdapter
import com.duyha.facebook.ui.user.adapter.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : BaseActivity<UserViewModel>() {

    private lateinit var binding: ActivityUserBinding
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUp() {
        initRepoRecyclerView()
        initSwipeToRefresh()
    }

    private fun initRepoRecyclerView() {
        binding.rcvRepo.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rcvRepo.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(adapter::retry),
            footer = LoadStateAdapter(adapter::retry)
        )
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setColorSchemeResources(R.color.accent)
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
        binding.viewNoNetwork.btnRetry.setOnClickListener { onRetryClick() }
    }

    private fun onRetryClick() {
        viewModel.getUser()
        adapter.retry()
        binding.viewNoNetwork.root.isVisible = false
    }

    override fun callViewModel() {
        viewModel.getUser()
    }

    override fun setObservers() {
        viewModel.user.observe(this) {
            showUser(it)
        }
        lifecycleScope.launch {
            viewModel.repoFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showUser(user: User) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.imgAvatar)
        binding.tvName.text = user.name
        binding.tvBio.text = user.bio
        binding.tvLocation.text = user.location
        binding.tvBlog.text = user.blog
    }

    override fun showError(err: ErrorEvent) {
        if (err == ErrorEvent.NO_NETWORK_CONNECTION) {
            binding.viewNoNetwork.root.isVisible = true
        } else {
            super.showError(err)
        }
    }

    override fun createViewModel(): UserViewModel =
        ViewModelProvider(this).get(UserViewModel::class.java)
}
