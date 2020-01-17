package com.android.githubusersearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils(private val context: Context) {

    private val connectivityStatus: Int
        get() {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            activeNetwork?.let {
                return when (it.type) {
                    ConnectivityManager.TYPE_WIFI -> TYPE_WIFI
                    ConnectivityManager.TYPE_MOBILE -> TYPE_MOBILE_DATA
                    else -> TYPE_NOT_CONNECTED
                }
            }
            return TYPE_NOT_CONNECTED
        }

    val isConnected: Boolean
        get() = connectivityStatus != TYPE_NOT_CONNECTED

    companion object {
        const val TYPE_WIFI = 1
        const val TYPE_MOBILE_DATA = 2
        const val TYPE_NOT_CONNECTED = 0
    }

}