package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MultiSearchDetail(
    @SerializedName("adult")
    val adult: Boolean? = false,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = emptyList(),

    @SerializedName("id")
    val id: Int,

    @SerializedName("original_name")
    val originalName: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("popularity")
    val popularity: BigDecimal? = BigDecimal.ZERO,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("vote_average")
    val voteAverage: BigDecimal? = BigDecimal.ZERO,

    @SerializedName("vote_count")
    val voteCount: Int? = Int.MIN_VALUE,

    @SerializedName("media_type")
    val mediaType: String
)