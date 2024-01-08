package com.dojo.moovies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentHomeBinding
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


internal class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val api: TheMovieDbApi by inject()

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

        initComponents()
    }

    private fun initComponents() {
        binding.fbSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_home_to_fg_search
            )
        }

        binding.btMyList.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_home_to_fg_mylist
            )
        }

        lifecycleScope.launch {
            api.getDiscoverMovies().let { response ->
                if (response.isSuccessful) {
                    val imgUrl = response.body()!!.results.random().posterPath!!
                    binding.ivPoster.loadFromTMDBApi(imgUrl, TmdbImageSize.POSTER_SIZE)

                    val coverUrl = response.body()!!.results.random().backdropPath!!
                    binding.ivCoverPoster.loadFromTMDBApi(coverUrl, TmdbImageSize.COVER_SIZE)
                }
            }

            api.getDiscoverTv().let { response ->
                if (response.isSuccessful) {
                    val imgUrl = response.body()!!.results.random().posterPath!!
                    binding.ivTv.loadFromTMDBApi(imgUrl, TmdbImageSize.POSTER_SIZE)
                }
            }
        }
    }

}