package com.dojo.moovies.ui.detail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.detail.adapter.StreamingBuyAdapter
import com.dojo.moovies.ui.detail.adapter.StreamingChanneAdapter
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.launch
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
    ): View {
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

        val detailMap = args.id to args.mediaType

        initStreamingList()
        initStreamingBuyList()
        initObservables()

        loadDetailInfo(detailMap)
        loadStreaminInfo(detailMap)
    }

    private fun initButtonsMyList(detail: MooviesDataSimplified) {
        binding.btMyList.setOnClickListener {
            viewModel.saveInMyList(detail)

            changeVisibilityWithAnimation(
                visible = binding.btMyListSaved,
                gone = binding.btMyList
            )

        }

        binding.btMyListSaved.setOnClickListener {
            viewModel.removeFromMyList(detail)

            changeVisibilityWithAnimation(
                visible = binding.btMyList,
                gone = binding.btMyListSaved
            )

        }
    }


    private fun loadStreaminInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadStreaming(detailMap)
            initObservables()
        }

    }

    private fun loadDetailInfo(detailMap: Pair<Int, String>) {
        verifyButtomMyList(detailMap)

        lifecycleScope.launch {
            viewModel.loadDetail(detailMap).let { detail ->
                detail?.let {
                    binding.ivCoverPoster.loadFromTMDBApi(
                        detail.posterPath,
                        TmdbImageSize.POSTER_SIZE_DETAIL
                    )

                    binding.tvDetailName.text = detail.name
                    binding.tvDetailOverview.text = detail.overview
                    binding.tvGenreList.text = detail.genreList
                    binding.tvDate.text = detail.releaseDate

                    initButtonsMyList(detail)
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
                binding.tvLabelStreaming.visibility = GONE
            } else {
                binding.tvLabelStreaming.visibility = VISIBLE
                streamingChanneAdapter.refresh(it)

            }
        }
    }

    private fun observableStreamingBuyList() = lifecycleScope.launch {
        viewModel.streamingBuy.collect {
            if (it.isEmpty()) {
                binding.tvLabelBuy.visibility = GONE
            } else {
                binding.tvLabelBuy.visibility = VISIBLE
                streamingBuyAdapter.refresh(it)

            }
        }
    }

    private fun verifyButtomMyList(detailMap: Pair<Int, String>) = lifecycleScope.launch {
        viewModel.checkIsMyList(detailMap).let { mylist ->
            if (mylist == null) {
                changeVisibility(
                    visible = binding.btMyList,
                    gone = binding.btMyListSaved
                )
            } else {
                changeVisibility(
                    visible = binding.btMyListSaved,
                    gone = binding.btMyList
                )
            }
        }
    }

    private fun changeVisibility(visible: Button, gone: Button) {
        visible.visibility = VISIBLE
        gone.visibility = GONE
    }


    private fun changeVisibilityWithAnimation(visible: Button, gone: Button) {
        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        visible.apply {
            alpha = 0f
            visibility = VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        gone.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    gone.visibility = GONE
                }
            })
    }

}

