package com.dojo.moovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentDetailBinding
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.api.TheMovieDbApi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val apiMotn: MovieOfTheNightApi by inject()

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

            apiTmdb.getTvDetail(2316).let { response ->
                if (response.isSuccessful)
                    binding.tvTvDetail.text =
                        response.body()!!.toString()
            }

            apiTmdb.getMovieDetail(667538).let { response ->
                if (response.isSuccessful)
                    binding.tvMovieDetail.text =
                        response.body()!!.toString()
            }

            apiMotn.getStreamingInfoByImdbId("tv/2316").let { response ->
                if (response.isSuccessful)
                    binding.tvStreamingList.text =
                        response.body()!!.result.streamingInfo.br.toString()
            }


        }
    }

}