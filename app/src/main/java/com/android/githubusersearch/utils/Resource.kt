package com.android.githubusersearch.utils

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable? = null
) {

    companion object {

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> empty(): Resource<T> {
            return Resource(Status.EMPTY, null, null)
        }

        fun <T> error(throwable: Throwable? = null): Resource<T> {
            return Resource(Status.ERROR, null, throwable)
        }
    }
}