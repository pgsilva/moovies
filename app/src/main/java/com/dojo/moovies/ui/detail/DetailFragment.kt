package com.dojo.moovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesProvider
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.detail.adapter.StreamingBuyAdapter
import com.dojo.moovies.ui.detail.adapter.StreamingChanneAdapter
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var streamingChanneAdapter: StreamingChanneAdapter

    private lateinit var streamingBuyAdapter: StreamingBuyAdapter

    private val viewModel: DetailViewModel by viewModel()

    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDependencies()
        initComponents()
    }

    private fun initDependencies() {
        streamingChanneAdapter = StreamingChanneAdapter()

        streamingBuyAdapter = StreamingBuyAdapter()
    }

    private fun initComponents() {
        binding.abToolbar.outlineProvider = null

        initStreamingList()
        initStreamingBuyList()

        initObservables()

        val detailMap = args.id to args.mediaType
        loadDetailInfo(detailMap)
        loadStreaminInfo(detailMap)
    }

    private fun loadStreaminInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadStreaming(detailMap).let { response ->
                response?.let {
                    initObservables()
                }
            }
        }

    }

    private fun loadDetailInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadDetail(detailMap).collect { detail ->
                detail?.let {
                    binding.ivCoverPoster.loadFromTMDBApi(
                        detail.backdropPath,
                        TmdbImageSize.COVER_SIZE
                    )

                    binding.ivDetailPoster.loadFromTMDBApi(
                        detail.posterPath,
                        TmdbImageSize.POSTER_SIZE
                    )

                    binding.tvDetailName.text = detail.name
                    binding.tvDetailOverview.text = detail.overview
                    binding.tvGenreList.text = detail.genreList
                    binding.tvDate.text = detail.releaseDate
                }

            }
        }
    }

    private fun initObservables() {
        observableStreamingList()
        observableStreamingBuyList()
    }

    private fun initStreamingList() {
        binding.rvStream.let { rv ->
            rv.adapter = streamingChanneAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initStreamingBuyList() {
        binding.rvStreamBuy.let { rv ->
            rv.adapter = streamingBuyAdapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observableStreamingList() = lifecycleScope.launch {
        viewModel.streamingList.collect {
            if (it.isEmpty()) {
                binding.tvLabelStreaming.visibility = View.GONE
            } else {
                binding.tvLabelStreaming.visibility = View.VISIBLE
                streamingChanneAdapter.refresh(it)

            }
        }
    }

    private fun observableStreamingBuyList() = lifecycleScope.launch {
        viewModel.streamingBuy.collect {
            if (it.isEmpty()) {
                binding.tvLabelBuy.visibility = View.GONE
            } else {
                binding.tvLabelBuy.visibility = View.VISIBLE
                streamingBuyAdapter.refresh(it)

            }
        }
    }

}