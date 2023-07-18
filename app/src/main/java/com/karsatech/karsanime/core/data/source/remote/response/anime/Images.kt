package com.karsatech.karsanime.core.data.source.remote.response.anime

import com.google.gson.annotations.SerializedName

data class Images(
    @field:SerializedName("jpg")
    val jpg: Jpg?,
) {
    data class Jpg(

        @field:SerializedName("image_url")
        val imageUrl: String?,

        @field:SerializedName("small_image_url")
        val smallImageUrl: String?,

        @field:SerializedName("large_image_url")
        val largeImageUrl: String?,
    )
}