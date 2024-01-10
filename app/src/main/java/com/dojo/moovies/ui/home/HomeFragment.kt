package com.dojo.moovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType.Companion.valueFromEnum
import com.dojo.moovies.databinding.FragmentHomeBinding
import com.dojo.moovies.ui.home.HomeCoverLoader.loadCoverImage
import com.dojo.moovies.ui.home.adapter.DiscoverMovieAdapter
import com.dojo.moovies.ui.home.adapter.DiscoverTvAdapter
import com.dojo.moovies.ui.home.adapter.PopularMovieAdapter
import com.dojo.moovies.ui.home.adapter.PopularTvAdapter
import com.dojo.moovies.ui.home.adapter.PreviewMyListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var discoverMovieAdapter: DiscoverMovieAdapter

    private lateinit var discoverTvAdapter: DiscoverTvAdapter

    private lateinit var previewMyListAdapter: PreviewMyListAdapter

    private lateinit var popularMovieAdapter: PopularMovieAdapter

    private lateinit var popularTvAdapter: PopularTvAdapter

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        loadCoverImage(binding.ivCoverPoster)

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


        popularTvAdapter = PopularTvAdapter {
            initDetailAction(it)
        }

        popularMovieAdapter = PopularMovieAdapter {
            initDetailAction(it)
        }
    }

    private fun initComponents() {
        binding.abToolbar.outlineProvider = null

        initSearchButton()
        initMyListButton()
        initDiscoverMovieList()
        initDiscoverTvList()
        initPreviewMyListList()
        initPopularMovieList()
        initPopularTvList()
        initObservables()
    }

    private fun initObservables() {

        observableDiscoverMovie()
        observableDiscoverTv()
        observablePreviewMyList()
        observablePopularMovie()
        observablePopularTv()
    }


    private fun observableDiscoverMovie() = lifecycleScope.launch {
        viewModel.discoverMovieList.collect {
            discoverMovieAdapter.refresh(it)
        }
    }

    private fun observableDiscoverTv() = lifecycleScope.launch {
        viewModel.discoverTvList.collect {
            discoverTvAdapter.refresh(it)

        }
    }

    private fun observablePreviewMyList() = lifecycleScope.launch {
        viewModel.previewMyList.collect {
            if (it.isEmpty()) {
                binding.tvMyList.visibility = GONE
            } else {
                binding.tvMyList.visibility = VISIBLE
                previewMyListAdapter.refresh(it)

            }
        }
    }

    private fun observablePopularMovie() = lifecycleScope.launch {
        viewModel.popularMovieList.collect {
            popularMovieAdapter.refresh(it)
        }
    }

    private fun observablePopularTv() = lifecycleScope.launch {
        viewModel.popularTvList.collect {
            popularTvAdapter.refresh(it)
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

    private fun initPopularMovieList() {
        binding.rvPopularMovie.let { rv ->
            rv.adapter = popularMovieAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initPopularTvList() {
        binding.rvPopularTv.let { rv ->
            rv.adapter = popularTvAdapter
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

    private fun initDetailAction(item: MooviesDataSimplified) {
        val direction = HomeFragmentDirections.actionFgHomeToFgDetail(
            item.id.toInt(),
            valueFromEnum(item.mediaType)
        )
        findNavController().navigate(direction)
    }

}