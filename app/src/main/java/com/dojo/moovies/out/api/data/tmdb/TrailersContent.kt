package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName

data class TrailersContent(
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val id: String,
    @SerializedName("published_at")
    val publishedAt : String?
)