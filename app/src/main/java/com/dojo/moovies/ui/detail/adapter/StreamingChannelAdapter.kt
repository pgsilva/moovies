package com.dojo.moovies.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.core.domain.MooviesWatchProvider
import com.dojo.moovies.databinding.ItemStreamListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class StreamingChannelAdapter(
    private val onSelect: (MooviesWatchProvider) -> Unit
) : RecyclerView.Adapter<StreamingChannelAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesWatchProvider>()

    inner class ViewHolder(private val binding: ItemStreamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val logo = binding.ivLogo

        fun bind(item: MooviesWatchProvider, onSelect: (MooviesWatchProvider) -> Unit) {
            logo.loadFromTMDBApi(item.logoPath, TmdbImageSize.POSTER_COVER_SIZE)

            binding.root.setOnClickListener {
                onSelect(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreamingChannelAdapter.ViewHolder =
        ItemStreamListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: StreamingChannelAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position], onSelect)

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<MooviesWatchProvider>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}