package com.dojo.moovies.repository.mapper

import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.out.api.data.tmdb.DiscoverMovie
import com.dojo.moovies.out.api.data.tmdb.DiscoverTv
import com.dojo.moovies.out.api.data.tmdb.MultiSearchDetail
import com.dojo.moovies.out.db.entity.MyListEntity


internal fun DiscoverMovie.toDomain(): MooviesData =
    MooviesData(
        id = this.id.toLong(),
        isAdult = this.adult,
        backdropPath = this.backdropPath,
        name = this.title,
        originalLanguage = this.originalLanguage,
        originalName = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        mediaType = MooviesMediaType.MOVIE,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount

    )

internal fun DiscoverTv.toDomain(): MooviesData =
    MooviesData(
        id = this.id.toLong(),
        isAdult = this.adult,
        backdropPath = this.backdropPath,
        name = this.name,
        originalLanguage = this.originalLanguage,
        originalName = this.originalName,
        overview = this.overview,
        posterPath = this.posterPath,
        mediaType = MooviesMediaType.TV,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount

    )

internal fun MyListEntity.toDomain(): MooviesData =
    MooviesData(
        id = this.id,
        isAdult = this.isAdult,
        backdropPath = this.backdropPath,
        name = this.name,
        originalLanguage = this.originalLanguage,
        originalName = this.originalName,
        overview = this.overview,
        posterPath = this.posterPath,
        mediaType = MooviesMediaType.valueFromString(this.mediaType),
        voteAverage = this.voteAverage.toBigDecimal(),
        voteCount = this.voteCount
    )

internal fun MultiSearchDetail.toDomain(): MooviesData {
    val name = this.name ?: this.title
    val originalName = this.originalName ?: this.title
    val poster = this.posterPath ?: this.backdropPath
    return MooviesData(
        id = this.id.toLong(),
        isAdult = this.adult,
        backdropPath = this.backdropPath,
        name = name ?: "No Name Provided",
        originalLanguage = this.originalLanguage ?: "en",
        originalName = originalName,
        overview = this.overview ?: "No description provided",
        posterPath = poster,
        mediaType = MooviesMediaType.TV,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}