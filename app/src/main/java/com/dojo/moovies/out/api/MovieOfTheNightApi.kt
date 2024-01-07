package com.dojo.moovies.out.api

import com.dojo.moovies.BuildConfig
import com.dojo.moovies.out.api.data.motn.StreamingInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

internal interface MovieOfTheNightApi {

    companion object {
        const val MOTN_API_URL = "https://streaming-availability.p.rapidapi.com/"
        const val MOTN_API_KEY = BuildConfig.MOTN_API_KEY
        const val MOTN_API_INITIAL_LANGUAGE = "en"

        private fun headers(): Map<String, String> =
            mapOf(
                "X-RapidAPI-Host" to "streaming-availability.p.rapidapi.com",
                "X-RapidAPI-Key" to MOTN_API_KEY
            )

    }

    @GET("/get")
    suspend fun getStreamingInfoByImdbId(
        @Query("tmdb_id") id: String,
        @Query("output_language") language: String = MOTN_API_INITIAL_LANGUAGE,
        @HeaderMap headers: Map<String, String> = headers()
    ): Response<StreamingInfoResponse>
}