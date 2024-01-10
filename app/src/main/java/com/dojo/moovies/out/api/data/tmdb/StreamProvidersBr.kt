package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName

data class StreamProvidersBr(
    @SerializedName("BR")
    val br: StreamProvider?
)

data class StreamProvider(
    val link: String?,
    @SerializedName("flatrate")
    val flatRate: List<Provider>?,
    val buy: List<Provider>?
)

data class Provider(
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("provider_id")
    val providerId: Int,
    @SerializedName("provider_name")
    val providerName: String,
    @SerializedName("display_priority")
    val displayPriority: Int?
)