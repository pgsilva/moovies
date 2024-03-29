package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesTrailerData
import com.dojo.moovies.core.domain.MooviesWatchProviders

class DetailInteractorState {
    sealed class DetailState {
        data class Success(val data: MooviesDataSimplified?) : DetailState()
        object Error : DetailState()
    }

    sealed class DetailStreamingListState {
        data class Success(val data: MooviesWatchProviders) : DetailStreamingListState()
        object Error : DetailStreamingListState()
    }

    sealed class MyListState {
        data class Success(val data: MooviesDataSimplified?) : MyListState()
        object Error : MyListState()
    }

    sealed class TrailerState {
        data class Success(val data: MooviesTrailerData?) : TrailerState()
        object Error : TrailerState()
    }
}