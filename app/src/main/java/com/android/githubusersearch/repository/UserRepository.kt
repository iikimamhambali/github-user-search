package com.android.githubusersearch.repository

import androidx.lifecycle.LiveData
import com.android.githubusersearch.base.BaseRepo
import com.android.githubusersearch.model.SearchData
import com.android.githubusersearch.model.SearchRequest
import com.android.githubusersearch.model.SearchResult
import com.android.githubusersearch.network.SearchServices
import com.android.githubusersearch.utils.ApiResponse
import com.android.githubusersearch.utils.AppExecutors
import com.android.githubusersearch.utils.Resource

class UserRepository(
    private val appExecutors: AppExecutors,
    private val service: SearchServices
) {

    fun getAllUser(request: SearchRequest): LiveData<Resource<SearchResult>> {
        return object : BaseRepo<SearchResult>(appExecutors) {
            override fun loadFromNetwork(): LiveData<ApiResponse<SearchResult>> {
                return service.getUser(request.query, request.page, request.limit)
            }
        }.asLiveData()
    }
}