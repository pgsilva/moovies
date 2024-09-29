package com.dojo.moovies.core.domain

data class MooviesActorData(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Long,
    val character: String,
    val creditId: String,
    val order: Long,
)