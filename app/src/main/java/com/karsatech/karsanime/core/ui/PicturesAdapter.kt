package com.karsatech.karsanime.core.ui

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.PictureItem
import com.karsatech.karsanime.core.utils.UiUtils.loadImage
import com.karsatech.karsanime.databinding.ItemPicturesBinding

class PicturesAdapter(private val context: Context) : ListAdapter<PictureItem, PicturesAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    private fun getScreen(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    private val screen = getScreen(context)
    private val imageWidth = screen / 3
    private val imageHeight = screen / 2

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PictureItem>() {
            override fun areItemsTheSame(
                oldItem: PictureItem,
                newItem: PictureItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PictureItem,
                newItem: PictureItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemPicturesBinding.inflate(
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

    inner class RecyclerViewHolder(private val bind: ItemPicturesBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: PictureItem) {
            with(bind) {

                val layoutParams = pictures.layoutParams
                layoutParams.width = imageWidth
                layoutParams.height = imageHeight
                pictures.layoutParams = layoutParams

                pictures.loadImage(data.jpg?.largeImageUrl, itemView.context, progressBar)
            }
        }
    }

    interface ActionAdapter {
        fun onClick(data: PictureItem)
    }
}