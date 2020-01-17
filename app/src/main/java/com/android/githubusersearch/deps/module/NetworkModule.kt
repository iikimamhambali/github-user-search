package com.android.githubusersearch.deps.module

import com.android.githubusersearch.utils.NetworkServiceFactory
import com.android.githubusersearch.utils.NetworkUtils
import org.koin.dsl.module

val networkModule = module {

    single { NetworkServiceFactory.makeNetworkService(get(), get(), get()) }

    single { NetworkServiceFactory.makeClientService(get(), get()) }

    single { NetworkServiceFactory.makeLoggingInterceptor() }

    single { NetworkServiceFactory.makeGson() }

    single { NetworkServiceFactory.makeCache(get()) }

    single { NetworkUtils(get()) }
}