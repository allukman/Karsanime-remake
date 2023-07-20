package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailGeneralResponse(

    @field:SerializedName("mal_id")
    val malId: Int?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("images")
    val images: Images?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("type")
    val type: String?,

    @field:SerializedName("source")
    val source: String?,

    @field:SerializedName("episodes")
    val episodes: Int?,

    @field:SerializedName("chapters")
    val chapters: Int?,

    @field:SerializedName("volumes")
    val volumes: Int?,

    @field:SerializedName("status")
    val status: String?,

    @field:SerializedName("rating")
    val rating: String?,

    @field:SerializedName("score")
    val score: Double?,

    @field:SerializedName("rank")
    val rank: Int?,

    @field:SerializedName("popularity")
    val popularity: Int?,

    @field:SerializedName("members")
    val members: Int?,

    @field:SerializedName("favorites")
    val favorite: Int?,

    @field:SerializedName("synopsis")
    val synopsis: String?,

    @field:SerializedName("genres")
    val genres: ArrayList<Genres>,

    ) : Parcelable
