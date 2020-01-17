package com.android.githubusersearch.network

import androidx.lifecycle.LiveData
import com.android.githubusersearch.utils.ApiResponse
import com.android.githubusersearch.model.SearchData
import com.android.githubusersearch.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchServices {

    @GET("search/users")
    fun getUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): LiveData<ApiResponse<SearchResult>>
}