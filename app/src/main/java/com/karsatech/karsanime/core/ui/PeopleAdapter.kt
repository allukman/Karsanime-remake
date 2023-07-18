package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemListPeopleBinding

class PeopleAdapter: ListAdapter<DetailPeopleResponse, PeopleAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

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
        val bind = ItemListPeopleBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemListPeopleBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: DetailPeopleResponse) {
            with(bind) {
                civImageGithubUser.loadImage(data.images?.jpg?.imageUrl, itemView.context, progressBar)
                name.text = data.name
            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: DetailPeopleResponse)
    }
}