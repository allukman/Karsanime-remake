package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureAnimeResponse(

	@field:SerializedName("data")
	val data: List<PictureItem>? = null
) : Parcelable

@Parcelize
data class PictureItem(

	@field:SerializedName("jpg")
	val jpg: Jpg? = null,

	@field:SerializedName("webp")
	val webp: Webp? = null
) : Parcelable
