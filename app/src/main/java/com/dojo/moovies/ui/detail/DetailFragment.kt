package com.dojo.moovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.db.StreamingChannelDao
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi
import com.dojo.moovies.ui.tryLoadSvg
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val apiMotn: MovieOfTheNightApi by inject()

    private val apiTmdb: TheMovieDbApi by inject()

    private val dao: StreamingChannelDao by inject()


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

        initComponents()
    }

    private fun initComponents() {
        lifecycleScope.launch {

//            apiTmdb.getTvDetail(2316).let { response ->
//                if (response.isSuccessful)
//                    binding.tvDetailOverview.text =
//                        response.body()!!.originalName + "-" + response.body()!!.id
//            }

            apiTmdb.getMovieDetail(569094).let { response ->
                if (response.isSuccessful) {
                    val detail = response.body()!!
                    binding.ivCoverPoster.loadFromTMDBApi(detail.backdropPath,
                        TmdbImageSize.COVER_SIZE
                    )

                    binding.ivDetailPoster.loadFromTMDBApi(detail.posterPath, TmdbImageSize.POSTER_SIZE)

                    binding.tvDetailName.text = detail.title
                    binding.tvOriginalLanguage.text = detail.originalLanguage
                    binding.tvDetailOverview.text = detail.overview
                }

            }

            apiMotn.getStreamingInfoByImdbId("movie/569094").let { response ->
                if (response.isSuccessful) {
                    val streamingList =
                        response.body()!!.result.streamingInfo.br

                    val channel = streamingList.first().service

                    binding.tvStreamingList.text = channel

                    dao.findById(channel).collect {
                        it?.let {
                            binding.ivStreamingLogo.tryLoadSvg(it.darkThemeImage)
                        }
                    }
                }
            }


        }
    }

}