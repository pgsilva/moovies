package com.dojo.moovies.interactor.state

import com.dojo.moovies.core.domain.MooviesDataSimplified

class MyListInteractorState {
    sealed class MyListState {
        data class Success(val data: List<MooviesDataSimplified>) : MyListState()
        object Error : MyListState()
    }
}