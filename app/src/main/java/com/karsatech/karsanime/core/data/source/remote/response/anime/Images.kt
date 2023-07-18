package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    @field:SerializedName("jpg")
    val jpg: Jpg?,
) : Parcelable {

    @Parcelize
    data class Jpg(

        @field:SerializedName("image_url")
        val imageUrl: String?,

        @field:SerializedName("small_image_url")
        val smallImageUrl: String?,

        @field:SerializedName("large_image_url")
        val largeImageUrl: String?,
    ) : Parcelable
}