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
        Log.i("MOOVIES-APP", "Home onViewCreated")

        initComponents()
    }

    private fun initComponents() {
        Toast.makeText(context, "Fragment Home", LENGTH_SHORT).show()

        binding.btPesquisa.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_home_to_fg_search
            )
        }

        lifecycleScope.launch{
            api.getDiscoverMovies().let { response ->

                if (response.isSuccessful) {
                    binding.textView2.text =
                        response.body()!!.catalogResults.map { it.resultTitle }.toString()
                }
            }
        }
    }

}