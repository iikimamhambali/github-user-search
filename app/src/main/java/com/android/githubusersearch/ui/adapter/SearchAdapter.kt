package com.android.githubusersearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.githubusersearch.R
import com.android.githubusersearch.model.SearchData

class SearchAdapter(private var data: MutableList<SearchData>) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_search_item, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(data[position])
    }
}