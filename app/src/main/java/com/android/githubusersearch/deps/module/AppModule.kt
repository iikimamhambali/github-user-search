package com.android.githubusersearch.deps.module

import com.android.githubusersearch.utils.AppExecutors
import org.koin.dsl.module

val appModule = module {
    single { AppExecutors() }
}