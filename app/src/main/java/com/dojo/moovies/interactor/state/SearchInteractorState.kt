package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesData

class SearchInteractorState {

    sealed class SearchState {
        data class Success(val data: List<MooviesData>) : SearchState()
        object Error : SearchState()
    }

}