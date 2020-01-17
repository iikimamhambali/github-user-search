package com.android.githubusersearch.base

import android.os.Bundle
import com.android.githubusersearch.utils.Resource
import com.android.githubusersearch.utils.Status

interface BaseView {

    fun getLayoutResId(): Int

    fun initView(savedInstanceState: Bundle?)

    fun initEvent()

    fun loadingData(isFromSwipe: Boolean = false)

    fun observeData()

    /**
     * Group function show and gone UI view progress bar
     */

    fun startLoading()

    fun stopLoading()

    /**
     * Group function show and gone state view
     */

    fun onInternetError()

    fun onDataNotFound()

    fun onServerBusy()

    fun onError(throwable: Throwable? = null)


    fun <T> parseObserveData(
        resource: Resource<T>,
        resultLoading: (T?) -> Unit = { startLoading() },
        resultSuccess: (T?) -> Unit = { _ -> },
        resultDataNotFound: (T?) -> Unit = { onDataNotFound() },
        resultServerBusy: (T?) -> Unit = { onServerBusy() },
        resultNetworkFailed: (Throwable?) -> Unit = { onInternetError() },
        resultError: (Throwable?) -> Unit = { onError(it) }
    ) {
        when (resource.status) {
            Status.LOADING -> {
                resultLoading(resource.data)
            }
            Status.SUCCESS -> {
                stopLoading()
                resource.data?.let { resultSuccess(it) }
            }
            Status.DATA_NOT_FOUND -> {
                stopLoading()
                resultDataNotFound(resource.data)
            }
            Status.SERVER_BUSY -> {
                stopLoading()
                resultServerBusy(resource.data)
            }
            Status.NETWORK_FAILED -> {
                stopLoading()
                resultNetworkFailed(resource.throwable)
            }
            Status.ERROR -> {
                stopLoading()
                resultError(resource.throwable)
            }
        }
    }
}