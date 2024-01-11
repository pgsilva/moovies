package com.dojo.moovies.ui.mylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dojo.moovies.R
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType.Companion.valueFromEnum
import com.dojo.moovies.databinding.ItemResultListBinding
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

class MyListAdapter(
    private val onSelect: (MooviesDataSimplified) -> Unit
) : RecyclerView.Adapter<MyListAdapter.ViewHolder>(), Filterable {

    private val dataset = mutableListOf<MooviesDataSimplified>()

    inner class ViewHolder(private val binding: ItemResultListBinding) :
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
    ): MyListAdapter.ViewHolder =
        ItemResultListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: MyListAdapter.ViewHolder, position: Int) =
        holder.bind(dataset[position], onSelect)

    override fun getItemCount(): Int = dataset.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()
                val filteredList: MutableList<MooviesDataSimplified> =
                    if (charString.isNullOrBlank()) {
                        dataset
                    } else {
                        dataset.filter {
                            (it.name.lowercase().contains(charString.lowercase())) or
                                    (valueFromEnum(it.mediaType).lowercase() == charString.lowercase())
                        }.toMutableList()
                    }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    val values = results.values as List<MooviesDataSimplified>
                    refresh(values)
                }
                notifyDataSetChanged()
            }
        }
    }

    fun refresh(items: List<MooviesDataSimplified>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }
}