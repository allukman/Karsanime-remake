package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.ExternalItem
import com.karsatech.karsanime.databinding.ItemExternalBinding
import com.karsatech.karsanime.databinding.ItemThemeBinding

class ExternalAdapter : ListAdapter<ExternalItem, ExternalAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExternalItem>() {
            override fun areItemsTheSame(
                oldItem: ExternalItem,
                newItem: ExternalItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ExternalItem,
                newItem: ExternalItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemExternalBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemExternalBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ExternalItem) {
            with(bind) {
                tvTitle.text = data.name

                tvTitle.setOnClickListener {
                    actionAdapter.onItemClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onItemClick(data: ExternalItem)
    }
}