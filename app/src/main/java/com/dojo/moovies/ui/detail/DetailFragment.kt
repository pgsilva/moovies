package com.dojo.moovies.ui.detail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType.Companion.valueFromEnum
import com.dojo.moovies.core.domain.MooviesWatchProvider
import com.dojo.moovies.core.domain.YOUTUBE_EMBED_URL
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.detail.adapter.CastAdapter
import com.dojo.moovies.ui.detail.adapter.SimilarAdapter
import com.dojo.moovies.ui.detail.adapter.StreamingBuyAdapter
import com.dojo.moovies.ui.detail.adapter.StreamingChannelAdapter
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var streamingChanneAdapter: StreamingChannelAdapter

    private lateinit var streamingBuyAdapter: StreamingBuyAdapter

    private lateinit var similarAdapter: SimilarAdapter

    private lateinit var castAdapter: CastAdapter

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
        streamingChanneAdapter = StreamingChannelAdapter {
            initLinkAction(it)
        }

        streamingBuyAdapter = StreamingBuyAdapter {
            initLinkAction(it)
        }

        similarAdapter = SimilarAdapter {
            initDetailAction(it)
        }

        castAdapter = CastAdapter()
    }

    private fun initComponents() {
        binding.abToolbar.outlineProvider = null

        val detailMap = args.id to args.mediaType

        initLoadingShimmer()
        initCastInfoList()
        initStreamingList()
        initStreamingBuyList()
        initSimilarList()
        initObservables()

        loadDetailInfo(detailMap)
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


    private fun loadCastInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadCast(detailMap)
            initObservables()
        }
    }

    private fun loadStreamingInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadStreaming(detailMap)
            initObservables()
        }

    }

    private fun loadSimilarInfo(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadSimilar(detailMap)
            initObservables()
        }
    }

    private fun loadTrailer(detailMap: Pair<Int, String>) {
        lifecycleScope.launch {
            viewModel.loadTrailer(detailMap).let { trailer ->
                trailer?.let {
                    val ytbView = binding.youtubeWebView

                    ytbView.webViewClient = object : WebViewClient() {

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String
                        ): Boolean {
                            return false
                        }
                    }

                    ytbView.settings.javaScriptEnabled = true
                    ytbView.settings.loadWithOverviewMode = true
                    ytbView.settings.useWideViewPort = true

                    ytbView.visibility = VISIBLE
                    binding.tvLabelVideos.visibility = VISIBLE

                    ytbView.loadUrl(YOUTUBE_EMBED_URL + trailer.key)
                }
            }
        }
    }

    private fun loadDetailInfo(detailMap: Pair<Int, String>) {
        verifyButtonMyList(detailMap)

        lifecycleScope.launch {
            viewModel.loadDetail(detailMap).let { detail ->
                detail?.let {

                    binding.ivCoverPoster.loadFromTMDBApi(
                        detail.backdropPath,
                        TmdbImageSize.COVER_SIZE
                    )

                    binding.ivDetailPoster.loadFromTMDBApi(
                        detail.posterPath,
                        TmdbImageSize.POSTER_SIZE_DETAIL
                    )

                    binding.tvDetailName.text = detail.name
                    if (detail.overview.isNotBlank())
                    //binding.tvLabelOverview.visibility = VISIBLE
                        binding.tvDetailOverview.text = detail.overview
                    else {
                        //binding.tvLabelOverview.visibility = GONE

                    }
                    binding.tvGenreList.text = detail.genreList
                    binding.tvDate.text = "Lançamento: ${detail.releaseDate}"

                    initButtonsMyList(detail)
                    loadCastInfo(detailMap)
                    loadStreamingInfo(detailMap)
                    loadTrailer(detailMap)
                    loadSimilarInfo(detailMap)

                    stopLoadingShimmer()
                }

            }
        }
    }

    private fun stopLoadingShimmer() {
        binding.isDetailFragment.root.visibility = GONE
        binding.svDetailContent.visibility = VISIBLE
    }

    private fun initLoadingShimmer() {
        binding.svDetailContent.visibility = GONE
        binding.isDetailFragment.root.visibility = VISIBLE

    }

    private fun initObservables() {
        observableCastInfo()
        observableStreamingList()
        observableStreamingBuyList()
        observableSimilarList()
    }

    private fun initStreamingList() {
        binding.rvStream.let { rv ->
            rv.adapter = streamingChanneAdapter
            rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initStreamingBuyList() {
        binding.rvStreamBuy.let { rv ->
            rv.adapter = streamingBuyAdapter
            rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initSimilarList() {
        binding.rvSimilar.let { rv ->
            rv.adapter = similarAdapter
            rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initCastInfoList() {
        binding.rvActor.let { rv ->
            rv.adapter = castAdapter
            rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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

    private fun observableSimilarList() = lifecycleScope.launch {
        viewModel.similar.collect {
            if (it.isEmpty()) {
                binding.tvSimilar.visibility = GONE
            } else {
                binding.tvSimilar.visibility = VISIBLE
                similarAdapter.refresh(it)

            }
        }
    }

    private fun observableCastInfo() = lifecycleScope.launch {
        viewModel.cast.collect {
            if (it.isEmpty()) {
                binding.tvLabelActor.visibility = GONE
            } else {
                binding.tvLabelActor.visibility = VISIBLE
                castAdapter.refresh(it)
            }
        }
    }

    private fun verifyButtonMyList(detailMap: Pair<Int, String>) = lifecycleScope.launch {
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
        val shortAnimationDuration =
            resources.getInteger(android.R.integer.config_shortAnimTime)

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

    private fun initLinkAction(it: MooviesWatchProvider) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(it.link)
        )
        startActivity(viewIntent)
    }

    private fun initDetailAction(item: MooviesDataSimplified) {
        val direction = DetailFragmentDirections.actionFgDetailToFgDetail(
            item.id.toInt(),
            valueFromEnum(item.mediaType)
        )
        findNavController().navigate(direction)
    }

}

