package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsItem
import com.karsatech.karsanime.core.utils.UiUtils.formatWithThousandsSeparator
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ItemNewsBinding

class NewsAdapter : ListAdapter<NewsItem, NewsAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(
                oldItem: NewsItem,
                newItem: NewsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NewsItem,
                newItem: NewsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemNewsBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemNewsBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: NewsItem) {
            with(bind) {

                title.text = data.title
                name.text = data.authorUsername
                date.text = data.date?.withDateFormat()
                excerpt.text = data.excerpt
                tvComment.text = data.comments?.formatWithThousandsSeparator()

                title.setOnClickListener {
                    actionAdapter.onNewsClick(data)
                }

                name.setOnClickListener {
                    actionAdapter.onAuthorClick(data)
                }

                civImagePeople.setOnClickListener {
                    actionAdapter.onAuthorClick(data)
                }

                excerpt.setOnClickListener {
                    actionAdapter.onNewsClick(data)
                }

                ivForum.setOnClickListener {
                    actionAdapter.onForumClick(data)
                }

            }
        }
    }

    interface ActionAdapter {
        fun onNewsClick(data: NewsItem)
        fun onForumClick(data: NewsItem)
        fun onAuthorClick(data: NewsItem)
    }
}