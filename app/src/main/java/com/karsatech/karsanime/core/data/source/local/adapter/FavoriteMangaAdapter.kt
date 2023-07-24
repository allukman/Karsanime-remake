package com.karsatech.karsanime.core.data.source.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListMangaPaginationBinding

class FavoriteMangaAdapter : ListAdapter<Manga, FavoriteMangaAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Manga>() {
            override fun areItemsTheSame(
                oldItem: Manga,
                newItem: Manga
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Manga,
                newItem: Manga
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListMangaPaginationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemListMangaPaginationBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Manga) {
            with(bind) {
                title.text = data.title
                chapters.text = data.chapters
                volumes.text = data.volumes
                status.text = data.status
                imagePoster.loadImage(data.image, itemView.context, progressBar)

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: Manga)
    }
}