package com.dojo.moovies.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.databinding.ItemStreamListBinding
import com.dojo.moovies.out.api.data.tmdb.Provider
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class StreamingBuyAdapter(
) : RecyclerView.Adapter<StreamingBuyAdapter.ViewHolder>() {

    private val dataset = mutableListOf<Provider>()

    inner class ViewHolder(private val binding: ItemStreamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val logo = binding.ivLogo
        fun bind(item: Provider) {
            logo.loadFromTMDBApi(item.logoPath, TmdbImageSize.LOGO_SIZE)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreamingBuyAdapter.ViewHolder =
        ItemStreamListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: StreamingBuyAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position])

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<Provider>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}