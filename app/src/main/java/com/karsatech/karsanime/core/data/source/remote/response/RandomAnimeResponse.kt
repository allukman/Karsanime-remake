package com.karsatech.karsanime.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RandomAnimeResponse (
    @field:SerializedName("data")
    val data: DetailAnimeItem
): Parcelable