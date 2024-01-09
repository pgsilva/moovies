package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesDataSimplified

class HomeInteractorState {

    sealed class HomeLoadState {
        data class Success(val data: List<MooviesDataSimplified>) : HomeLoadState()
        object Error : HomeLoadState()
    }
}