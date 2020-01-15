package com.android.githubusersearch

import com.android.githubusersearch.base.BaseApplication
import com.android.githubusersearch.deps.library
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubSearchApp : BaseApplication() {

    override fun initApplication() {
        startKoin {
            modules(library)
            androidContext(this@GithubSearchApp)
        }
    }
}