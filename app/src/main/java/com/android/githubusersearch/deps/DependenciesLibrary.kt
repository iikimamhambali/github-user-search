package com.android.githubusersearch.deps

import com.android.githubusersearch.deps.module.appModule
import com.android.githubusersearch.deps.module.networkModule
import com.android.githubusersearch.deps.module.repositoryModule
import com.android.githubusersearch.deps.module.viewModelModule

val library = listOf(appModule, networkModule, viewModelModule, repositoryModule)