package com.dojo.moovies.out.api

internal sealed class ApiResult<out T> {
    data class Success<out T>(val resultSuccess: T): ApiResult<T>()
    data class Error(val exception: Exception?, val message: String?): ApiResult<Nothing>()
}