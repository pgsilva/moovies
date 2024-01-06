package com.dojo.moovies.out.api.data

import com.google.gson.annotations.SerializedName

internal data class DiscoverResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<DiscoverMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int

)