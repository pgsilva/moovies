package com.dojo.moovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.repository.mapper.toDomain
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi
import com.dojo.moovies.ui.tryLoadSvg
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding


    private val apiTmdb: TheMovieDbApi by inject()



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

            apiTmdb.getMovieDetail(569094).let { response ->
                if (response.isSuccessful) {
                    val detail = response.body()!!
                    val data = detail.toDomain()
                    binding.ivCoverPoster.loadFromTMDBApi(data.backdropPath,
                        TmdbImageSize.COVER_SIZE
                    )

                    binding.ivDetailPoster.loadFromTMDBApi(data.posterPath, TmdbImageSize.POSTER_SIZE)

                    binding.tvDetailName.text = data.name
                    binding.tvOriginalLanguage.text = data.originalLanguage
                    binding.tvDetailOverview.text = data.overview
                    binding.tvGenreList.text = data.genreList
                    binding.tvDate.text = data.releaseDate
                }

            }



        }
    }

}