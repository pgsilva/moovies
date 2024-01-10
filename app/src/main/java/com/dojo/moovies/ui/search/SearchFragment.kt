package com.dojo.moovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.databinding.FragmentSearchBinding
import com.dojo.moovies.ui.home.HomeFragmentDirections
import com.dojo.moovies.ui.search.adapter.SearchListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var searchListAdapter: SearchListAdapter

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDependencies()
        initComponents()
        initObservables()
    }

    private fun initObservables() = lifecycleScope.launch {
        viewModel.searchList.collect {
            searchListAdapter.refresh(it)
        }
    }

    private fun initDependencies() {
        searchListAdapter = SearchListAdapter {
            initDetailAction(it)
        }
    }

    private fun initDetailAction(item: MooviesDataSimplified) {
        val direction = SearchFragmentDirections.actionFgSearchToFgDetail(
            item.id.toInt(),
            MooviesMediaType.valueFromEnum(item.mediaType)
        )
        findNavController().navigate(direction)
    }

    private fun initComponents() {
        initSearchInput()
        initSearchList()
    }

    private fun initSearchList() {
        binding.rvSearchItems.let { rv ->
            rv.adapter = searchListAdapter
            rv.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSearchInput() {
        binding.svSearchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank())
                    viewModel.findBy(query)
                else viewModel.clearHistoricSearch()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank())
                    viewModel.findBy(newText)
                else viewModel.clearHistoricSearch()

                return false
            }
        })
    }
}