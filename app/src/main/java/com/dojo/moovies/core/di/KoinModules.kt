package com.dojo.moovies.core.di

import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.TheMovieDbApi.Companion.TMDB_API_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    factory {
        Retrofit.Builder()
            .baseUrl(TMDB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(TheMovieDbApi::class.java) }
}

