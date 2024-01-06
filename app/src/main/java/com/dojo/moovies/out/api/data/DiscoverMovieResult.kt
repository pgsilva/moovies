package com.dojo.moovies.out.api.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class DiscoverMovieResult(
    @SerializedName("adult")
    val resultAdult: Boolean,
    @SerializedName("backdrop_path")
    val resultBackdropPath: String?,
    @SerializedName("genre_ids")
    val resultGenreIds: List<Int>,
    @SerializedName("id")
    val resultId: Int,
    @SerializedName("original_language")
    val resultOriginalLanguage: String,
    @SerializedName("original_title")
    val resultOriginalTitle: String,
    @SerializedName("overview")
    val resultOverview: String,
    @SerializedName("popularity")
    val resultPopularity: BigDecimal,
    @SerializedName("poster_path")
    val resultPosterPath: String?,
    @SerializedName("release_date")
    val resultReleaseDate: String,
    @SerializedName("title")
    val resultTitle: String,
    @SerializedName("video")
    val resultVideo: Boolean,
    @SerializedName("vote_average")
    val resultVoteAverage: BigDecimal,
    @SerializedName("vote_count")
    val resultVoteCount: Int
)