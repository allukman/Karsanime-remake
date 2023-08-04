package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodesAnimeResponse(

	@field:SerializedName("data")
	val data: List<EpisodesAnimeItem>? = null
) : Parcelable

@Parcelize
data class EpisodesAnimeItem(

	@field:SerializedName("title_japanese")
	val titleJapanese: String? = null,

	@field:SerializedName("forum_url")
	val forumUrl: String? = null,

	@field:SerializedName("aired")
	val aired: String? = null,

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("title_romanji")
	val titleRomanji: String? = null,

	@field:SerializedName("filler")
	val filler: Boolean? = null,

	@field:SerializedName("recap")
	val recap: Boolean? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
