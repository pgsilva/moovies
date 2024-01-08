package com.dojo.moovies.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.databinding.ItemPreviewMyListListBinding
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class PreviewMyListAdapter(
    private val onSelect: (MooviesData) -> Unit
) : RecyclerView.Adapter<PreviewMyListAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesData>()

    inner class ViewHolder(private val binding: ItemPreviewMyListListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val poster = binding.ivPoster
        private val name = binding.tvName

        fun bind(item: MooviesData, onSelect: (MooviesData) -> Unit) {
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
    ): PreviewMyListAdapter.ViewHolder =
        ItemPreviewMyListListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: PreviewMyListAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position], onSelect)

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<MooviesData>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}