package com.android.githubusersearch.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView(savedInstanceState)
        initEvent()
        loadingData()
        observeData()
    }

    override fun initView(savedInstanceState: Bundle?) {}

    override fun initEvent() {}

    override fun loadingData(isFromSwipe: Boolean) {}

    override fun observeData() {}

    override fun startLoading() {}

    override fun stopLoading() {}

    override fun onInternetError(){}

    override fun onDataNotFound(){}

    override fun onServerBusy(){}

    override fun onError(throwable: Throwable?){}
}