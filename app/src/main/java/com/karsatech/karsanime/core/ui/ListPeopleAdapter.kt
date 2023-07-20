package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListPeoplePaginationBinding

class ListPeopleAdapter: PagingDataAdapter<DetailPeopleResponse, ListPeopleAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailPeopleResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailPeopleResponse,
                newItem: DetailPeopleResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailPeopleResponse,
                newItem: DetailPeopleResponse
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
        fun bind(data: DetailPeopleResponse) {
            with(bind) {
                civImagePeople.loadImage(data.images?.jpg?.imageUrl, itemView.context, progressBar)
                name.text = data.name
                favorites.text = data.favorites.toString()

                itemView.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: DetailPeopleResponse)
    }
}