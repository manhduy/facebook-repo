package com.duyha.facebook.ui.user.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duyha.facebook.R
import com.duyha.facebook.data.model.Repo
import com.duyha.facebook.databinding.ItemRepoBinding

class RepoViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
) {

    private val biding = ItemRepoBinding.bind(itemView)

    fun bind(item: Repo?) {
        item?.let { it ->
            biding.root.setOnClickListener {
                item.htmlUrl.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    biding.root.context.startActivity(intent)
                }
            }
            biding.tvTitle.text = it.fullName
            biding.tvDesc.text = it.description
            biding.tvLanguage.text = it.language
            biding.tvFork.text = it.forksCount.toString()
            biding.tvStar.text = it.stargazersCount.toString()
            biding.tvIssue.text = it.issueCount.toString()
            biding.updateTime.text = it.updatedAt
        }
    }
}
