package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Detail>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class DiscoverTvResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Detail>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MultiResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Detail>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class StreamResponse(
    val id: Int,
    val results: StreamProvidersBr,
)

data class TrailerResponse(
    val id: Int,
    val results: List<TrailersContent>,
)