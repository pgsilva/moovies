package com.dojo.moovies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesWatchProvider
import com.dojo.moovies.interactor.DetailInteractor
import com.dojo.moovies.interactor.state.DetailInteractorState
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

    private val _streamingList = MutableStateFlow(emptyList<MooviesWatchProvider>())

    val streamingList = _streamingList.asStateFlow()

    private val _streamingBuy = MutableStateFlow(emptyList<MooviesWatchProvider>())

    val streamingBuy = _streamingBuy.asStateFlow()

    suspend fun loadDetail(map: Pair<Int, String>): MooviesDataSimplified? {
        val state = interactor.load(map)
        return if (state is DetailInteractorState.DetailState.Success)
            state.data
        else null
    }

    fun loadStreaming(map: Pair<Int, String>) {
        viewModelScope.launch {
            val state = interactor.loadStreaming(map)
            if (state is DetailInteractorState.DetailStreamingListState.Success) {
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
}
