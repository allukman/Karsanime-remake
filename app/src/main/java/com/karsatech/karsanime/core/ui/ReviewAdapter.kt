package com.karsatech.karsanime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.ReviewItem
import com.karsatech.karsanime.core.utils.UiUtils.formatWithThousandsSeparator
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ItemReviewBinding

class ReviewAdapter(private val recyclerView: RecyclerView) : ListAdapter<ReviewItem, ReviewAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    private var lastClickedPosition: Int = RecyclerView.NO_POSITION

    private fun setLastClickedPosition(position: Int) {
        lastClickedPosition = position
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewItem,
                newItem: ReviewItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ReviewItem,
                newItem: ReviewItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            setLastClickedPosition(position)
            actionAdapter.onClick(getItem(position))
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemReviewBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ReviewItem) {
            with(bind) {

                name.text = data.user?.username
                tags.text = data.tags!![0]
                review.text = data.review
                date.text = data.date?.withDateFormat()
                overall.text = data.reactions?.overall?.formatWithThousandsSeparator()

                if (data.isExpanded) {
                    review.maxLines = Integer.MAX_VALUE
                    readMore.text = itemView.context.getString(R.string.show_less)
                    ivReadMore.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    review.maxLines = 5
                    readMore.text = itemView.context.getString(R.string.read_more)
                    ivReadMore.setImageResource(R.drawable.ic_arrow_down)
                }

                layoutReadMore.setOnClickListener {
                    data.isExpanded = !data.isExpanded
                    notifyItemChanged(adapterPosition)

                    if (!data.isExpanded && adapterPosition == lastClickedPosition) {
                        // Scroll to the last clicked position when "Show Less" is clicked
                        recyclerView.smoothScrollToPosition(lastClickedPosition)
                    }
                }
            }
        }
    }

    interface ActionAdapter {
        fun onClick(data: ReviewItem)
    }
}