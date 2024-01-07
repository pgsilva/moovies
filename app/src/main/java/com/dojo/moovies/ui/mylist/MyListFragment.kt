package com.dojo.moovies.ui.mylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dojo.moovies.R
import com.dojo.moovies.databinding.FragmentMylistBinding
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.ui.load
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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

        lifecycleScope.launch {

            dao.update(
                MyListEntity(
                    2316,
                    false,
                    "/7DJKHzAi83BmQrWLrYYOqcoKfhR.jpg",
                    "The Office",
                    "en",
                    "The Office",
                    "descricao",
                    "/7DJKHzAi83BmQrWLrYYOqcoKfhR.jpg",
                    null,
                    null,
                    null
                )
            )
            delay(200)
            dao.findAll().collect { list ->
                binding.textView4.text = list.toString()
                binding.imageView.loadFromTMDBApi(list.first().posterPath)
            }
        }
    }

}