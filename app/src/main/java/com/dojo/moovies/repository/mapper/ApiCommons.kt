package com.dojo.moovies.repository.mapper

import retrofit2.Response

object RetrofitCommons {

    @Suppress("UNCHECKED_CAST")
    fun <T> Response<T>.extractResponse(): T =
        if (this.isSuccessful) {
            this.body() as T
        } else {
            throw Exception()
        }
}