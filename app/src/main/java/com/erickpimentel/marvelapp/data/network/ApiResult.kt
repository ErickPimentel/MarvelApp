package com.erickpimentel.marvelapp.data.network

import java.lang.Exception

sealed class ApiResult<out T> {
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    data class Success<T>(val response: T) : ApiResult<T>()
}