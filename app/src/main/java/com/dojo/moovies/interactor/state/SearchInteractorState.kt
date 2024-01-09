package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesDataSimplified

class SearchInteractorState {

    sealed class SearchState {
        data class Success(val data: List<MooviesDataSimplified>) : SearchState()
        object Error : SearchState()
    }

}