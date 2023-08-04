package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterAnimeResponse(

	@field:SerializedName("data")
	val data: List<CharacterAnimeItem>? = null
) : Parcelable

@Parcelize
data class Character(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class Person(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class VoiceActorsItem(

	@field:SerializedName("person")
	val person: Person? = null,

	@field:SerializedName("language")
	val language: String? = null
) : Parcelable

@Parcelize
data class CharacterAnimeItem(

	@field:SerializedName("favorites")
	val favorites: Int? = null,

	@field:SerializedName("character")
	val character: Character? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("voice_actors")
	val voiceActors: List<VoiceActorsItem?>? = null
) : Parcelable
