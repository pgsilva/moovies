package com.dojo.moovies.out.api.data.tmdb

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class MovieDetail (
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("imdb_id")
    val imdb_id: String?,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("title")
    val title: String,

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
