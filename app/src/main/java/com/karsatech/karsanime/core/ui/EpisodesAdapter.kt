package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeItem
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ItemEpisodesBinding

class EpisodesAdapter : ListAdapter<EpisodesAnimeItem, EpisodesAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EpisodesAnimeItem>() {
            override fun areItemsTheSame(
                oldItem: EpisodesAnimeItem,
                newItem: EpisodesAnimeItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EpisodesAnimeItem,
                newItem: EpisodesAnimeItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemEpisodesBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemEpisodesBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: EpisodesAnimeItem) {
            with(bind) {

                number.text = data.malId.toString()
                title.text = data.title
                titleRomanji.text = data.titleRomanji
                score.text = data.score.toString()
                aired.text = data.aired?.withDateFormat()


                title.setOnClickListener {
                    actionAdapter.onTitleClick(data)
                }

                titleRomanji.setOnClickListener {
                    actionAdapter.onTitleClick(data)
                }

                number.setOnClickListener {
                    actionAdapter.onTitleClick(data)
                }

                iconForum.setOnClickListener {
                    actionAdapter.onForumClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onTitleClick(data: EpisodesAnimeItem)
        fun onForumClick(data: EpisodesAnimeItem)
    }
}