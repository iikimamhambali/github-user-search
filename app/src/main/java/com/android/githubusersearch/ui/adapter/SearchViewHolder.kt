package com.android.githubusersearch.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.githubusersearch.R
import com.android.githubusersearch.model.SearchData
import com.android.githubusersearch.utils.loadFromUrlWithPlaceholder
import kotlinx.android.synthetic.main.layout_search_item.view.*

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: SearchData) {
        with(itemView) {
            ivAvatarUser.loadFromUrlWithPlaceholder(
                data.avatarUrl,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher
            )
            tvSearchContent.text = data.name
        }
    }
}