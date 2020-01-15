package com.android.githubusersearch.deps.module

import com.android.githubusersearch.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { UserRepository(get(), get()) }
}