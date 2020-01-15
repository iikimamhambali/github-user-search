package com.android.githubusersearch.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.android.githubusersearch.R
import com.android.githubusersearch.base.BaseActivity
import com.android.githubusersearch.base.BaseRecyclerview
import com.android.githubusersearch.model.SearchData
import com.android.githubusersearch.model.SearchRequest
import com.android.githubusersearch.ui.adapter.SearchAdapter
import com.android.githubusersearch.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private var resultList = mutableListOf<SearchData>()
    private var query = ""
    private var currentPage: Int = 0
    private var page: Int = 0
    private var isLoad = false

    private val adapterSearch by lazy { SearchAdapter(resultList) }
    private val viewModel by viewModel<MainViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setupInitialSearch()
        setupRecylerview()
        setupScrollListener()
        getSearchData()
    }

    private fun setupRecylerview() {
        with(rvUser) {
            initRecyclerView(adapterSearch, BaseRecyclerview.LayoutManager.VERTICAL)
            smoothScrollToPosition(0)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupScrollListener() {
        nested.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null && currentPage < page && !isLoad) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    loadingData(currentPage, query)
                }
            }
        }
    }

    private fun setupEmptyState() {
        tvStateContent.visibility = View.VISIBLE
        tvStateContent.text = getString(R.string.label_empty_search)
    }

    private fun setupInitialSearch() {
        tvStateContent.visibility = View.VISIBLE
        tvStateContent.text = getString(R.string.label_initial_search)
    }

    private fun goneTextState() {
        tvStateContent.visibility = View.GONE
    }

    private fun showProgressbar(visible: Boolean) {
        when (visible) {
            true -> goneTextState()
            else -> goneTextState()
        }
    }

    private fun loadingData(page: Int, query: String) {
        viewModel.getAllUser(SearchRequest(query = query, page = page, limit = 10))
    }

    private fun getSearchData() {
        svUser?.setMinCount(1)
        svUser?.setHintLabel(getString(R.string.label_search_github_user))
        svUser?.getQueryInput()?.observe(this, Observer {
            when {
                it.isEmpty() -> setupInitialSearch()
                it.isNotEmpty() -> {
                    query = it
                    currentPage = 0
                    clearData()
                    loadingData(page = currentPage, query = query)
                }
            }
        })
        svUser?.setAssetClear(R.drawable.ic_clear_black)
        svUser?.setAssetSearch(R.drawable.ic_search_black)
        svUser?.setAssetClearDisable(R.drawable.ic_clear_grey)
    }

    private fun addData(list: List<SearchData>) {
        val positionStart = resultList.size + 1
        val itemCount = list.size
        resultList.addAll(list)
        adapterSearch.notifyItemRangeInserted(positionStart, itemCount)
    }

    private fun clearData() {
        resultList.clear()
        adapterSearch.notifyDataSetChanged()
    }

    override fun observeData() {
        super.observeData()
        viewModel.userList.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> showProgressbar(isLoad)
                Status.SUCCESS -> {
                    isLoad = false
                    it.data?.let { result ->
                        if (result.item.isNullOrEmpty()) {
                            setupEmptyState()
                            rvUser.visibility = View.GONE
                            return@Observer
                        }
                        addData(result.item)
                        currentPage++
                        page = result.totalCount
                    }
                }
                Status.EMPTY -> {
                    isLoad = false
                    setupEmptyState()
                    rvUser.visibility = View.GONE
                }
                Status.ERROR -> {
                }
            }
        })
    }

}
