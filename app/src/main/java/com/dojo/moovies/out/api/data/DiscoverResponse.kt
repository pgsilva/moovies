package com.dojo.moovies.out.api.data

import com.google.gson.annotations.SerializedName

internal data class DiscoverResponse(
    @SerializedName("page")
    val catalogPage: Int,
    @SerializedName("results")
    val catalogResults: List<DiscoverMovie>,
    @SerializedName("total_pages")
    val catalogTotalPages: Int,
    @SerializedName("total_results")
    val catalogTotalResults: Int

)