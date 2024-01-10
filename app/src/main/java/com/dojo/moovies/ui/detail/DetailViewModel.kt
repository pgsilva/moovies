package com.dojo.moovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.interactor.DetailInteractor
import com.dojo.moovies.interactor.state.DetailInteractorState
import com.dojo.moovies.out.api.data.tmdb.Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val interactor: DetailInteractor
) : ViewModel() {

    private val _streamingList = MutableStateFlow(emptyList<Provider>())

    val streamingList = _streamingList.asStateFlow()

    private val _streamingBuy = MutableStateFlow(emptyList<Provider>())

    val streamingBuy = _streamingBuy.asStateFlow()

    fun loadDetail(map: Pair<Int, String>): Flow<MooviesDataSimplified?> = flow {
        interactor.load(map).collect { state ->
            if (state is DetailInteractorState.DetailState.Success)
                emit(state.data)
        }
    }

    fun loadStreaming(map: Pair<Int, String>) {
        viewModelScope.launch {
            interactor.loadStreaming(map)
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is DetailInteractorState.DetailStreamingListState.Success) {
                        if (state.data.buy != null)
                            _streamingBuy.update { state.data.buy }

                        if (state.data.flatRate != null)
                            _streamingList.update { state.data.flatRate }
                    }
                }
        }
    }

}