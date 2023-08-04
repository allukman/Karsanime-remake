package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsAnimeResponse(

	@field:SerializedName("data")
	val data: List<NewsItem?>? = null

) : Parcelable

@Parcelize
data class JpgNews(

	@field:SerializedName("image_url")
	val imageUrl: String? = null
) : Parcelable

@Parcelize
data class ImagesNews(

	@field:SerializedName("jpg")
	val jpg: JpgNews? = null
) : Parcelable

@Parcelize
data class NewsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("forum_url")
	val forumUrl: String? = null,

	@field:SerializedName("images")
	val images: ImagesNews? = null,

	@field:SerializedName("author_url")
	val authorUrl: String? = null,

	@field:SerializedName("comments")
	val comments: Int? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("excerpt")
	val excerpt: String? = null,

	@field:SerializedName("author_username")
	val authorUsername: String? = null,

	@field:SerializedName("url")
	val url: String? = null

) : Parcelable

