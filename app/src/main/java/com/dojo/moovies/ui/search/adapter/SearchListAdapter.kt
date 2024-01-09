package com.dojo.moovies.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.databinding.ItemSearchResultListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class SearchListAdapter(
    private val onSelect: (MooviesDataSimplified) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesDataSimplified>()

    inner class ViewHolder(private val binding: ItemSearchResultListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val poster = binding.ivCoverPoster
        private val name = binding.tvName

        fun bind(item: MooviesDataSimplified, onSelect: (MooviesDataSimplified) -> Unit) {
            binding.clItemSearch.startAnimation(
                AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.anim_down
                )
            )

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

    fun refresh(items: List<MooviesDataSimplified>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}