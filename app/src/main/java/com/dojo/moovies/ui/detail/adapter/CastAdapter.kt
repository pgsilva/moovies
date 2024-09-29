package com.dojo.moovies.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.core.domain.MooviesActorData
import com.dojo.moovies.databinding.ItemActorListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class CastAdapter : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private val dataset = mutableListOf<MooviesActorData>()

    inner class ViewHolder(private val binding: ItemActorListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MooviesActorData) {
            binding.ivActor.loadFromTMDBApi(item.profilePath, TmdbImageSize.POSTER_SIZE)
            binding.tvActorName.text = item.name
            binding.tvActorCharacter.text = item.character
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastAdapter.ViewHolder =
        ItemActorListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position])

    override fun getItemCount(): Int = dataset.size

    fun refresh(items: List<MooviesActorData>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}