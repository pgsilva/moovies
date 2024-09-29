package com.dojo.moovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesTrailerData
import com.dojo.moovies.core.domain.MooviesWatchProvider
import com.dojo.moovies.interactor.DetailInteractor
import com.dojo.moovies.interactor.state.DetailInteractorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val interactor: DetailInteractor
) : ViewModel() {

    private val _streamingList = MutableStateFlow(emptyList<MooviesWatchProvider>())

    val streamingList = _streamingList.asStateFlow()

    private val _streamingBuy = MutableStateFlow(emptyList<MooviesWatchProvider>())

    val streamingBuy = _streamingBuy.asStateFlow()

    private val _similar = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val similar = _similar.asStateFlow()

    suspend fun loadDetail(map: Pair<Int, String>): MooviesDataSimplified? {
        val state = interactor.load(map)
        return if (state is DetailInteractorState.DetailState.Success)
            state.data
        else null
    }

    fun loadStreaming(map: Pair<Int, String>) {
        viewModelScope.launch {
            val state = interactor.loadStreaming(map)
            if (state is DetailInteractorState.StreamingListState.Success) {
                if (state.data.buy != null)
                    _streamingBuy.update { state.data.buy }

                if (state.data.flatRate != null)
                    _streamingList.update { state.data.flatRate }
            }
        }
    }

    suspend fun checkIsMyList(detailMap: Pair<Int, String>): MooviesDataSimplified? {
        val state = interactor.loadMyListByIdAndMediaType(detailMap)
        return if (state is DetailInteractorState.MyListState.Success) {
            state.data
        } else null
    }

    fun saveInMyList(mooviesDataSimplified: MooviesDataSimplified) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveInMyList(mooviesDataSimplified)
        }
    }

    fun removeFromMyList(mooviesDataSimplified: MooviesDataSimplified) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.removeFromMyList(mooviesDataSimplified)
        }
    }

    suspend fun loadTrailer(map: Pair<Int, String>): MooviesTrailerData? {
        val state = interactor.loadTrailer(map)
        return if (state is DetailInteractorState.TrailerState.Success)
            state.data
        else null
    }

    fun loadSimilar(map: Pair<Int, String>) {
        viewModelScope.launch {
            val state = interactor.loadSimilar(map)
            if (state is DetailInteractorState.SimilarListState.Success) {
                _similar.update { state.data }
            }
        }
    }
}
