package com.android.githubusersearch.network

import androidx.lifecycle.LiveData
import com.android.githubusersearch.utils.ApiResponse
import com.android.githubusersearch.model.SearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchServices {

    @GET("users")
    fun getUser(
        @Query("page") page: Int = 0,
        @Query("per_page") limit: Int = 10
    ): LiveData<ApiResponse<List<SearchData>>>
}