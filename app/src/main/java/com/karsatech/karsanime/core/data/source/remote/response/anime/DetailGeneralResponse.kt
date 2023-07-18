package com.karsatech.karsanime.core.data.source.remote.response.anime

import com.google.gson.annotations.SerializedName

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

    @field:SerializedName("status")
    val status: String?,
)
