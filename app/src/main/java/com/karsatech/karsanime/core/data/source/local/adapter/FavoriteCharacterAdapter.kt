package com.karsatech.karsanime.core.data.source.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.domain.model.Character
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListCharacterBinding
import com.karsatech.karsanime.databinding.ItemListPeoplePaginationBinding

class FavoriteCharacterAdapter: ListAdapter<Character, FavoriteCharacterAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(
                oldItem: Character,
                newItem: Character
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Character,
                newItem: Character
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListCharacterBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemListCharacterBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Character) {
            with(bind) {
                name.text = data.name
                civImagePeople.loadImage(data.image, itemView.context, progressBar)
                favorites.text = data.favorites

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: Character)
    }
}