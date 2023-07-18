package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.Genres
import com.karsatech.karsanime.databinding.ItemListGenreBinding

class GenreAdapter : ListAdapter<Genres, GenreAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genres>() {
            override fun areItemsTheSame(
                oldItem: Genres,
                newItem: Genres
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Genres,
                newItem: Genres
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListGenreBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemListGenreBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Genres) {
            with(bind) {
                title.text = data.name

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: Genres)
    }
}