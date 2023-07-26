package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListMangaPaginationBinding

class ListMangaAdapter : PagingDataAdapter<DetailAnimeItem, ListMangaAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

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
        fun bind(data: DetailAnimeItem) {
            with(bind) {
                title.text = data.title
                chapters.text = "${data.chapters} chapters"
                volumes.text = "${data.volumes} volumes"
                status.text = data.status
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