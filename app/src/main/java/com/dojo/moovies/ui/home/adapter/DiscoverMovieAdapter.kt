package com.dojo.moovies.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.databinding.ItemHomeListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class DiscoverMovieAdapter(
    private val onSelect: (MooviesDataSimplified) -> Unit
) : RecyclerView.Adapter<DiscoverMovieAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesDataSimplified>()

    inner class ViewHolder(private val binding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val poster = binding.ivPoster
        private val name = binding.tvName

        fun bind(item: MooviesDataSimplified, onSelect: (MooviesDataSimplified) -> Unit) {
            item.posterPath?.let {
                poster.loadFromTMDBApi(it, TmdbImageSize.POSTER_SIZE)
            }
            name.text = item.name

            binding.root.setOnClickListener {
                onSelect(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverMovieAdapter.ViewHolder =
        ItemHomeListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: DiscoverMovieAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position], onSelect)

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<MooviesDataSimplified>){
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}