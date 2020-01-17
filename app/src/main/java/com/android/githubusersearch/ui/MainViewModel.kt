package com.android.githubusersearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.githubusersearch.base.BaseViewModel
import com.android.githubusersearch.model.SearchRequest
import com.android.githubusersearch.model.SearchResult
import com.android.githubusersearch.repository.UserRepository
import com.android.githubusersearch.utils.Resource

class MainViewModel(repository: UserRepository) : BaseViewModel() {

    private val userData = MutableLiveData<SearchRequest>()

    val userList: LiveData<Resource<SearchResult>> = Transformations
        .switchMap(userData) {
            repository.getAllUser(it)
        }

    fun getAllUser(data: SearchRequest) {
        userData.value = data
    }
}