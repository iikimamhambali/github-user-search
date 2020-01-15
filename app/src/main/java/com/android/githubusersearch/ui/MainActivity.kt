package com.android.githubusersearch.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.android.githubusersearch.R
import com.android.githubusersearch.base.BaseActivity
import com.android.githubusersearch.base.BaseRecyclerview
import com.android.githubusersearch.model.SearchData
import com.android.githubusersearch.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private val data = mutableListOf<SearchData>()
    private var query = ""
    private var currentPage: Int = 0

    private val adapterSearch by lazy { SearchAdapter(data) }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setupRecylerview()
        getSearchData()
    }

    override fun initEvent() {
        super.initEvent()
    }

    private fun setupRecylerview() {
        with(rvUser) {
            initRecyclerView(adapterSearch, BaseRecyclerview.LayoutManager.VERTICAL)
        }
    }

    private fun getSearchData() {
        svUser?.setMinCount(3)
        svUser?.setHintLabel(getString(R.string.label_search_github_user))
        svUser?.getQueryInput()?.observe(this, Observer {
            when {
                it.isEmpty() -> toast("EMPTY")
                it.length >= 3 -> {
                    toast(it)
                }
            }
        })
        svUser?.setAssetClear(R.drawable.ic_clear_black)
        svUser?.setAssetSearch(R.drawable.ic_search_black)
        svUser?.setAssetClearDisable(R.drawable.ic_clear_grey)
    }

    private fun addData() {

    }

    private fun clearData() {

    }

    private fun replaceData() {

    }
}
