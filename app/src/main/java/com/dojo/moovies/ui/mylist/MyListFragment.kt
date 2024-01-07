package com.dojo.moovies.ui.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentMylistBinding
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MyListFragment : Fragment(R.layout.fragment_mylist) {

    private lateinit var binding: FragmentMylistBinding

    private val dao: MyListDao by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMylistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun initComponents() {
        binding.btDetalhe.setOnClickListener {
            findNavController().navigate(
                R.id.action_fg_mylist_to_fg_detail
            )
        }
        lifecycleScope.launch {
            dao.findAll().collect { list ->
                if (list.isNotEmpty()) {
                    binding.textView4.text =
                        list.first().originalName + " " + list.first().originalLanguage
                    binding.imageView.loadFromTMDBApi(list.first().posterPath)
                }
            }
        }
    }

}