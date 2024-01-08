package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesData

class HomeInteractorState {

    sealed class HomeLoadState {
        data class Success(val data: List<MooviesData>) : HomeLoadState()
        object Error : HomeLoadState()
    }
}