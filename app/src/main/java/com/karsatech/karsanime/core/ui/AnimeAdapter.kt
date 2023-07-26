package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListUpcomingBinding

class AnimeAdapter : ListAdapter<DetailAnimeItem, AnimeAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailAnimeItem>() {
            override fun areItemsTheSame(
                oldItem: DetailAnimeItem,
                newItem: DetailAnimeItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailAnimeItem,
                newItem: DetailAnimeItem
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
        fun bind(data: DetailAnimeItem) {
            with(bind) {
             title.text = data.title
             imagePoster.loadImage(data.images!!.jpg!!.largeImageUrl, itemView.context, progressBar)

             itemView.setOnClickListener {
                actionAdapter.onItemClick(data)
             }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: DetailAnimeItem)
    }
}