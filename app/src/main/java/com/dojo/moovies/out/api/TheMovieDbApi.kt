package com.dojo.moovies.out.api

import com.dojo.moovies.BuildConfig
import com.dojo.moovies.out.api.data.tmdb.CreditDetail
import com.dojo.moovies.out.api.data.tmdb.DiscoverMovieResponse
import com.dojo.moovies.out.api.data.tmdb.DiscoverTvResponse
import com.dojo.moovies.out.api.data.tmdb.Detail
import com.dojo.moovies.out.api.data.tmdb.MultiResponse
import com.dojo.moovies.out.api.data.tmdb.StreamResponse
import com.dojo.moovies.out.api.data.tmdb.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TheMovieDbApi {
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
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = true,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<DiscoverMovieResponse>

    @GET("/3/discover/tv")
    suspend fun getDiscoverTv(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("sort_by") sortBy: String = TMDB_API_INITIAL_SORT_BY,
        @Query("include_adult") adult: Boolean = false,
        @Query("include_null_first_air_dates") firstAirData: Boolean = false,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<DiscoverTvResponse>

    @GET("/3/search/multi")
    suspend fun getMultiByQuery(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("include_adult") adult: Boolean = false,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<MultiResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE
    ): Response<Detail>

    @GET("/3/tv/{series_id}")
    suspend fun getTvDetail(
        @Path("series_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE
    ): Response<Detail>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<DiscoverMovieResponse>

    @GET("/3/tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE
    ): Response<DiscoverTvResponse>


    @GET("/3/movie/{movie_id}/watch/providers")
    suspend fun getMovieStreaming(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<StreamResponse>

    @GET("/3/tv/{tv_id}/watch/providers")
    suspend fun getTvStreaming(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<StreamResponse>

    @GET("/3/tv/{tv_id}/videos")
    suspend fun getTrailerTv(
        @Path("tv_id") id: Int,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<TrailerResponse>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailerMovie(
        @Path("movie_id") id: Int,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<TrailerResponse>


    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<DiscoverMovieResponse>

    @GET("/3/tv/{tv_id}/similar")
    suspend fun getSimilarTv(
        @Path("tv_id") id: Int,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<DiscoverTvResponse>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = TMDB_API_INITIAL_PAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<CreditDetail>


    @GET("/3/tv/{tv_id}/credits")
    suspend fun getCreditsTv(
        @Path("tv_id") id: Int,
        @Query("language") language: String = TMDB_API_INITIAL_LANGUAGE,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<CreditDetail>

}