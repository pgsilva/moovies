package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesDataSimplified
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
}