package com.android.githubusersearch.deps.module

import com.android.githubusersearch.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { MainViewModel(get()) }
}