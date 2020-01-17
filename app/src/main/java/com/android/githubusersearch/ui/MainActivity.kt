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
import com.android.githubusersearch.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private var resultList = mutableListOf<SearchData>()
    private var query = ""
    private var currentPage: Int = 0
    private var page: Int = 0
    private var isLoad = false
    private var hitCount = 0

    private val networkUtils by inject<NetworkUtils>()
    private val adapterSearch by lazy { SearchAdapter(resultList) }
    private val viewModel by viewModel<MainViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setupTextState(getString(R.string.label_initial_search))
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
            if (v.getChildAt(v.childCount - 1) != null && currentPage <= page && !isLoad) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    loadingData(currentPage, query)
                }
            }
        }
    }

    private fun setupTextState(messange: String? = "") {
        tvStateContent.visibility = View.VISIBLE
        tvStateContent.text = messange
    }

    private fun goneTextState() {
        tvStateContent.visibility = View.GONE
    }

    private fun loadingData(page: Int, query: String = "") {
        when {
            networkUtils.isConnected -> {
                when (hitCount) {
                    10 -> setupTextState(getString(R.string.label_server_busy))
                    else -> viewModel.getAllUser(SearchRequest(query = query, page = page, limit = 30))
                }
            }
            else -> setupTextState(getString(R.string.label_lost_connection))
        }

    }

    private fun getSearchData() {
        svUser?.setMinCount(1)
        svUser?.setHintLabel(getString(R.string.label_search_github_user))
        svUser?.getQueryInput()?.observe(this, Observer {
            when {
                it.isEmpty() -> {
                    setupTextState(getString(R.string.label_initial_search))
                    rvUser.visibility = View.GONE
                }
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
        resultList.addAll(list)
        adapterSearch.notifyDataSetChanged()
    }

    private fun clearData() {
        resultList.clear()
        adapterSearch.notifyDataSetChanged()
    }

    override fun observeData() {
        super.observeData()
        viewModel.userList.observe(this, Observer {
            parseObserveData(it,
                resultSuccess = { result ->
                    result?.let {
                        if (result.item.isNullOrEmpty()) {
                            setupTextState(getString(R.string.label_empty_search))
                            rvUser.visibility = View.GONE
                            return@parseObserveData
                        }
                        addData(result.item)
                        currentPage++
                        hitCount++
                        page = result.totalCount
                    }
                }, resultDataNotFound = {
                    setupTextState(getString(R.string.label_empty_search))
                    rvUser.visibility = View.GONE
                })
        })
    }

    override fun startLoading() {
        isLoad = true
        when (currentPage) {
            0 -> {
                sectionProgress?.visibility = View.VISIBLE
                sectionProgress?.startShimmer()
            }
            else -> pbHorizontal?.visibility = View.VISIBLE
        }
        goneTextState()
    }

    override fun stopLoading() {
        isLoad = false
        when (currentPage) {
            0 -> {
                sectionProgress?.stopShimmer()
                sectionProgress?.visibility = View.GONE
            }
            else -> pbHorizontal?.visibility = View.GONE
        }
        sectionProgress?.visibility = View.GONE
    }
}
