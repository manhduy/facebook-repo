package com.duyha.facebook.ui.user

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.duyha.facebook.databinding.ActivityUserBinding
import com.duyha.facebook.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity<UserViewModel>() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUp() {
    }

    override fun callViewModel() {
    }

    override fun setObservers() {
    }

    override fun createViewModel(): UserViewModel =
        ViewModelProvider(this).get(UserViewModel::class.java)
}
