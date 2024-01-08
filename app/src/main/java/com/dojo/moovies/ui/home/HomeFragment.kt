package com.dojo.moovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.databinding.FragmentHomeBinding
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.home.adapter.DiscoverMovieAdapter
import com.dojo.moovies.ui.home.adapter.DiscoverTvAdapter
import com.dojo.moovies.ui.home.adapter.PreviewMyListAdapter
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var discoverMovieAdapter: DiscoverMovieAdapter

    private lateinit var discoverTvAdapter: DiscoverTvAdapter

    private lateinit var previewMyListAdapter: PreviewMyListAdapter

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDependencies()
        initComponents()
    }

    private fun initDependencies() {
        discoverMovieAdapter = DiscoverMovieAdapter {
            initDetailAction(it)
        }

        discoverTvAdapter = DiscoverTvAdapter {
            initDetailAction(it)
        }

        previewMyListAdapter = PreviewMyListAdapter {
            initDetailAction(it)
        }
    }

    private fun initComponents() {
        initSearchButton()
        initMyListButton()
        initDiscoverMovieList()
        initDiscoverTvList()
        initPreviewMyListList()
        initObservables()
    }

    private fun initObservables() {
        observableDiscoverMovie()
        observableDiscoverTv()
        observablePreviewMyList()
    }


    private fun observableDiscoverMovie() = lifecycleScope.launch {
        viewModel.discoverMovieList.collect {
            discoverMovieAdapter.refresh(it)

            if (it.isNotEmpty()) {
                val coverUrl = it.random().backdropPath
                binding.ivCoverPoster.loadFromTMDBApi(coverUrl, TmdbImageSize.POSTER_COVER_SIZE)
            }
        }
    }

    private fun observableDiscoverTv() = lifecycleScope.launch {
        viewModel.discoverTvList.collect {
            discoverTvAdapter.refresh(it)
        }
    }

    private fun observablePreviewMyList() = lifecycleScope.launch {
        viewModel.previewMyList.collect {
            previewMyListAdapter.refresh(it)
        }
    }


    private fun initDiscoverMovieList() {
        binding.rvDiscoverMovie.let { rv ->
            rv.adapter = discoverMovieAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initDiscoverTvList() {
        binding.rvDiscoverTv.let { rv ->
            rv.adapter = discoverTvAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initPreviewMyListList() {
        binding.rvMyList.let { rv ->
            rv.adapter = previewMyListAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initMyListButton() {
        binding.btMyList.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_home_to_fg_mylist
            )
        }
    }

    private fun initSearchButton() {
        binding.ibSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_home_to_fg_search
            )
        }
    }

    private fun initDetailAction(item: MooviesData) {
        findNavController().navigate(
            R.id.action_fg_home_to_fg_detail
        )
    }

}