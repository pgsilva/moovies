package com.dojo.moovies.out.api

import com.dojo.moovies.out.api.data.DiscoverResponse
import com.dojo.moovies.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


internal interface TheMovieDbApi {
    companion object {
        const val TMDB_API_URL = "https://api.themoviedb.org/"
        const val TMDB_API_INITIAL_PAGE = 1
        const val TMDB_API_INITIAL_SORT_BY = "popularity.desc"
        const val TMDB_API_INITIAL_LANGUAGE = "pt-BR"
        const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    }

    @GET("/3/discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("sort_by") sortBy: String = TMDB_API_INITIAL_SORT_BY,
        @Query("include_adult") adult: Boolean = true,
        @Query("include_video") video: Boolean = true,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<DiscoverResponse>
}