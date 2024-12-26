package com.naji.translation.api

sealed class Resource<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(msg: String) : Resource<T>(msg = msg)
}
