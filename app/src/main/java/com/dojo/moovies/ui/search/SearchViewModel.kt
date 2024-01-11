package com.dojo.moovies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.interactor.SearchInteractor
import com.dojo.moovies.interactor.state.HomeInteractorState
import com.dojo.moovies.interactor.state.SearchInteractorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val interactor: SearchInteractor
) : ViewModel(

) {
    private val _searchList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val searchList = _searchList.asStateFlow()

    internal fun findBy(query: String) {
        clearHistoricSearch()

        viewModelScope.launch {
            val state = interactor.findBy(query)
            if (state is SearchInteractorState.SearchState.Success)
                _searchList.update { state.data }
        }
    }

    fun clearHistoricSearch() {
        viewModelScope.launch {
            _searchList.update { emptyList() }
        }
    }
}