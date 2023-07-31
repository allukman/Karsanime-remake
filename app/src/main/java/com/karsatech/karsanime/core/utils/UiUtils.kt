package com.karsatech.karsanime.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.karsatech.karsanime.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object UiUtils {

    fun ImageView.loadImage(url : String?, context : Context, progressBar : ProgressBar) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

            })
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .dontAnimate()
            .into(this)
    }

    fun CircleImageView.loadImage(url : String?, context : Context, progressBar : ProgressBar) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

            })
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .dontAnimate()
            .into(this)
    }

    fun String.withDateFormat(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        val date = inputFormat.parse(this) ?: return ""

        val outputFormat = SimpleDateFormat("MMMM, yyyy", Locale.US)
        return outputFormat.format(date)
    }

    fun getCurrentYear(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDate.now()
            return currentDate.year
        } else {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.YEAR)
        }
    }

    fun String.capitalizeFirstLetter(): String {
        if (isEmpty()) return this
        return substring(0, 1).uppercase(Locale.getDefault()) + substring(1)
    }

}