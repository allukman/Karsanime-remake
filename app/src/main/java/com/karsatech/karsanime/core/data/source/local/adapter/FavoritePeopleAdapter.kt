package com.karsatech.karsanime.core.data.source.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListMangaPaginationBinding
import com.karsatech.karsanime.databinding.ItemListPeoplePaginationBinding

class FavoritePeopleAdapter : ListAdapter<People, FavoritePeopleAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemListPeoplePaginationBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemListPeoplePaginationBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: People) {
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
        fun onItemClick(data: People)
    }
}