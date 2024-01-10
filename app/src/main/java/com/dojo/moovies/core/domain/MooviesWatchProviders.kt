package com.dojo.moovies.core.domain

data class MooviesWatchProviders(
    //TODO REFATORAR PARA CLASSES PROPRIAS
    val buy: List<MooviesWatchProvider>?,
    val flatRate: List<MooviesWatchProvider>?
)

data class MooviesWatchProvider(
    val logoPath: String,
    val providerId: Int,
    val providerName: String,
    val displayPriority: Int?
)