package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeStatisticResponse(

	@field:SerializedName("data")
	val data: StatisticItem? = null
) : Parcelable

@Parcelize
data class ScoresItem(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null,

	@field:SerializedName("votes")
	val votes: Int? = null
) : Parcelable

@Parcelize
data class StatisticItem(

	@field:SerializedName("plan_to_watch")
	val planToWatch: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("scores")
	val scores: List<ScoresItem?>? = null,

	@field:SerializedName("dropped")
	val dropped: Int? = null,

	@field:SerializedName("completed")
	val completed: Int? = null,

	@field:SerializedName("on_hold")
	val onHold: Int? = null,

	@field:SerializedName("watching")
	val watching: Int? = null
) : Parcelable
