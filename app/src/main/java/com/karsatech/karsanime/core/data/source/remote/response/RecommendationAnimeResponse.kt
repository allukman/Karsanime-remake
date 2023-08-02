package com.karsatech.karsanime.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecommendationAnimeResponse(

	@field:SerializedName("data")
	val data: List<RecommendationItem?>? = null
) : Parcelable

@Parcelize
data class Images(

	@field:SerializedName("jpg")
	val jpg: Jpg? = null,

	@field:SerializedName("webp")
	val webp: Webp? = null
) : Parcelable

@Parcelize
data class Entry(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class RecommendationItem(

	@field:SerializedName("entry")
	val entry: Entry? = null,

	@field:SerializedName("votes")
	val votes: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class Jpg(

	@field:SerializedName("large_image_url")
	val largeImageUrl: String? = null,

	@field:SerializedName("small_image_url")
	val smallImageUrl: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null
) : Parcelable

@Parcelize
data class Webp(

	@field:SerializedName("large_image_url")
	val largeImageUrl: String? = null,

	@field:SerializedName("small_image_url")
	val smallImageUrl: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null
) : Parcelable
