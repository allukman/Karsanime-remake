package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.RecommendationItem
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListUpcomingBinding

class RecommendationAnimeAdapter :
    ListAdapter<RecommendationItem, RecommendationAnimeAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationItem>() {
            override fun areItemsTheSame(
                oldItem: RecommendationItem,
                newItem: RecommendationItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecommendationItem,
                newItem: RecommendationItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListUpcomingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemListUpcomingBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: RecommendationItem) {
            with(bind) {
                val anime = data.entry

                title.text = anime?.title
                imagePoster.loadImage(
                    anime?.images?.jpg?.largeImageUrl,
                    itemView.context,
                    progressBar
                )

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: RecommendationItem)
    }
}