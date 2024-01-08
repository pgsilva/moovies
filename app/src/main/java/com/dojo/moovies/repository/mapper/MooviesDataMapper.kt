package com.dojo.moovies.repository.mapper

import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.out.api.data.tmdb.DiscoverMovie
import com.dojo.moovies.out.api.data.tmdb.DiscoverTv
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