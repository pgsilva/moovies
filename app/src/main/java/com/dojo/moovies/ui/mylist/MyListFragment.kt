package com.dojo.moovies.ui.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.core.domain.MooviesMediaType.Companion.valueFromEnum
import com.dojo.moovies.databinding.FragmentMylistBinding
import com.dojo.moovies.ui.mylist.adapter.MyListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyListFragment : Fragment(R.layout.fragment_mylist) {

    private lateinit var binding: FragmentMylistBinding

    private lateinit var myListAdapter: MyListAdapter

    private val viewModel: MyListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMylistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDependencies()
        initComponents()
        initObservables()
    }

    private fun initDependencies() {
        myListAdapter = MyListAdapter {
            initDetailAction(it)
        }
    }

    private fun initComponents() {
        binding.abToolbar.outlineProvider = null

        binding.rvMyList.let { rv ->
            rv.adapter = myListAdapter
            rv.layoutManager = LinearLayoutManager(context)
        }

        configureChipButtons()
        configureSearchInput()
    }

    private fun configureChipButtons() {
        binding.cpMovies.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myListAdapter.filter.filter(valueFromEnum(MooviesMediaType.MOVIE))
                binding.cpTv.isChecked = false
            } else {
                initObservables()
            }
        }

        binding.cpTv.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myListAdapter.filter.filter(valueFromEnum(MooviesMediaType.TV))
                binding.cpMovies.isChecked = false
            } else {
                initObservables()
            }
        }
    }

    private fun initObservables() = lifecycleScope.launch {
        viewModel.myList.collect {
            myListAdapter.refresh(it)
        }
    }

    private fun initDetailAction(item: MooviesDataSimplified) {
        val direction = MyListFragmentDirections.actionFgMylistToFgDetail(
            item.id.toInt(),
            MooviesMediaType.valueFromEnum(item.mediaType)
        )
        findNavController().navigate(direction)
    }

    private fun configureSearchInput() {
        binding.svSearchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank())
                    initObservables()
                else
                    myListAdapter.filter.filter(query)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank())
                    initObservables()
                else
                    myListAdapter.filter.filter(newText)

                return false
            }
        })
    }

}