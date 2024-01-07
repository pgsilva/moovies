package com.dojo.moovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentSearchBinding
import com.dojo.moovies.out.api.TheMovieDbApi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    private val api: TheMovieDbApi by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun initComponents() {
        binding.btDetalhe.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_search_to_fg_detail
            )
        }

        lifecycleScope.launch {
            api.getMultiByQuery("La casa de Papel").let { response ->
                if (response.isSuccessful) {
                    binding.tvSearchQuery.text =
                        response.body()!!.results.mapNotNull { it.originalName }.toString()
                }
            }
        }

    }
}