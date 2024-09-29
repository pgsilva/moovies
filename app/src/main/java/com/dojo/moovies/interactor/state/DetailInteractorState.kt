package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesActorData
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesTrailerData
import com.dojo.moovies.core.domain.MooviesWatchProviders

class DetailInteractorState {
    sealed class DetailState {
        data class Success(val data: MooviesDataSimplified?) : DetailState()
        object Error : DetailState()
    }

    sealed class StreamingListState {
        data class Success(val data: MooviesWatchProviders) : StreamingListState()
        object Error : StreamingListState()
    }

    sealed class SimilarListState {
        data class Success(val data: List<MooviesDataSimplified>) : SimilarListState()
        object Error : SimilarListState()
    }

    sealed class CastListState {
        data class Success(val data: List<MooviesActorData>) : CastListState()
        object Error : CastListState()
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