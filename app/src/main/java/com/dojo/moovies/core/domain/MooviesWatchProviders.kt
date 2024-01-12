package com.dojo.moovies.core.domain

data class MooviesWatchProviders(
    val buy: List<MooviesWatchProvider>?,
    val flatRate: List<MooviesWatchProvider>?
)

data class MooviesWatchProvider(
    val link: String?,
    val logoPath: String,
    val providerId: Int,
    val providerName: String,
    val displayPriority: Int?
)