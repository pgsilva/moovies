package com.dojo.moovies.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.databinding.ItemSearchResultListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class SearchListAdapter(
    private val onSelect: (MooviesData) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesData>()

    inner class ViewHolder(private val binding: ItemSearchResultListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val poster = binding.ivCoverPoster
        private val name = binding.tvName

        fun bind(item: MooviesData, onSelect: (MooviesData) -> Unit) {
            poster.loadFromTMDBApi(item.posterPath, TmdbImageSize.POSTER_SIZE)
            name.text = item.name

            binding.root.setOnClickListener {
                onSelect(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.ViewHolder =
        ItemSearchResultListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: SearchListAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position], onSelect)

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<MooviesData>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}