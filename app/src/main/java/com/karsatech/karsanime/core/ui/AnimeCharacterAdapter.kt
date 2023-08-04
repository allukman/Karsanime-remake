package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeItem
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemCharacterBinding

class AnimeCharacterAdapter : ListAdapter<CharacterAnimeItem, AnimeCharacterAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterAnimeItem>() {
            override fun areItemsTheSame(
                oldItem: CharacterAnimeItem,
                newItem: CharacterAnimeItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CharacterAnimeItem,
                newItem: CharacterAnimeItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemCharacterBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemCharacterBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: CharacterAnimeItem) {
            with(bind) {
                name.text = data.character?.name
                role.text = data.role
                favorites.text = data.favorites.toString()
                ivCharacter.loadImage(data.character?.images?.jpg?.imageUrl, itemView.context, progressBar)

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: CharacterAnimeItem)
    }
}