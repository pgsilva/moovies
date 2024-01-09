package com.dojo.moovies.core.domain

import com.dojo.moovies.out.api.data.tmdb.Provider

data class MooviesProvider(
    //TODO REFATORAR PARA CLASSES PROPRIAS
    val buy: List<Provider>?,
    val flatRate: List<Provider>?
)