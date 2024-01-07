package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName

internal data class DiscoverMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<DiscoverMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

internal data class DiscoverTvResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<DiscoverTv>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

internal data class MultiResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MultiSearchDetail>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)