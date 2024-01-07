package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class DiscoverTv (
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("id")
    val id: Int,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: BigDecimal,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("vote_average")
    val voteAverage: BigDecimal,

    @SerializedName("vote_count")
    val voteCount: Int
)