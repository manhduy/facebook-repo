package com.duyha.facebook.ui.user.adapter

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
            biding.tvTitle.text = it.fullName
            biding.tvDesc.text = it.description
            biding.tvLanguage.text = it.language
            biding.tvFork.text = it.forksCount.toString()
            biding.tvStar.text = it.stargazersCount.toString()
            biding.tvIssue.text = it.issuesCount.toString()
            biding.tvUpdateTime.text =
                    itemView.context.getString(R.string.text_holder_update_time, it.updatedAt)
        }
    }
}
