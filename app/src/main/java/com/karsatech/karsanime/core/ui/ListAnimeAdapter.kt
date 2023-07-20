package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListAnimeBinding

class ListAnimeAdapter : ListAdapter<DetailGeneralResponse, ListAnimeAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailGeneralResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailGeneralResponse,
                newItem: DetailGeneralResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailGeneralResponse,
                newItem: DetailGeneralResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListAnimeBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemListAnimeBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: DetailGeneralResponse) {
            with(bind) {
                title.text = data.title
                description.text = data.synopsis
                imagePoster.loadImage(data.images!!.jpg!!.largeImageUrl, itemView.context, progressBar)

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: DetailGeneralResponse)
    }
}