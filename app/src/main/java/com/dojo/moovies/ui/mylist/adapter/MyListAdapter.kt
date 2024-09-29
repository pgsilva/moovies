package com.dojo.moovies.ui.mylist.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
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
    private val onSelect: (MooviesDataSimplified) -> Unit,
    private val onSelectWatch: (MooviesDataSimplified, Boolean) -> Unit
) : RecyclerView.Adapter<MyListAdapter.ViewHolder>(), Filterable {

    private val dataset = mutableListOf<MooviesDataSimplified>()

    inner class ViewHolder(private val binding: ItemResultListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val poster = binding.ivCoverPoster
        private val name = binding.tvName

        fun bind(
            item: MooviesDataSimplified,
            onSelect: (MooviesDataSimplified) -> Unit,
            onSelectWatch: (MooviesDataSimplified, Boolean) -> Unit
        ) {

//            binding.clItemSearch.startAnimation(
//                AnimationUtils.loadAnimation(
//                    binding.root.context,
//                    R.anim.anim_down
//                )
//            )

            poster.loadFromTMDBApi(item.posterPath, TmdbImageSize.POSTER_SIZE)
            name.text = item.name

            binding.root.setOnClickListener {
                onSelect(item)
            }

            if (item.watched) {
                binding.btWatched.visibility = Button.VISIBLE
                binding.btNotWatched.visibility = Button.GONE
            } else {
                binding.btWatched.visibility = Button.GONE
                binding.btNotWatched.visibility = Button.VISIBLE
            }

            binding.btWatched.setOnClickListener {
                changeVisibilityWithAnimation(
                    visible = binding.btNotWatched,
                    gone = binding.btWatched
                )

                onSelectWatch(item, item.watched)
            }

            binding.btNotWatched.setOnClickListener {
                changeVisibilityWithAnimation(
                    visible = binding.btWatched,
                    gone = binding.btNotWatched
                )
                onSelectWatch(item, item.watched)
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

    override fun onBindViewHolder(holder: MyListAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position], onSelect, onSelectWatch)

    }

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

    fun sortWatched() {
        dataset.sortByDescending { it.watched }
        notifyDataSetChanged()
    }

    fun sortNotWatched() {
        dataset.sortBy { it.watched }
        notifyDataSetChanged()
    }


    fun refresh(items: List<MooviesDataSimplified>) {
        dataset.clear()
        dataset.addAll(items)

        notifyDataSetChanged()
    }

    //TODO reaproveitar a mesma funcao de Detail e evitar duplicacao
    private fun changeVisibilityWithAnimation(visible: Button, gone: Button) {
        val shortAnimationDuration = 200

        visible.apply {
            alpha = 0f
            visibility = VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        gone.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    gone.visibility = GONE
                }
            })
    }
}

