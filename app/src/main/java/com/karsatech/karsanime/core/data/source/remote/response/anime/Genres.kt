package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres (

    @field:SerializedName("mal_id")
    val malId: Int?,

    @field:SerializedName("type")
    val type: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("url")
    val url: String?,

    ) : Parcelable