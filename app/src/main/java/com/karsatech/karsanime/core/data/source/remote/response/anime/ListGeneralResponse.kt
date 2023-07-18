package com.karsatech.karsanime.core.data.source.remote.response.anime

import com.google.gson.annotations.SerializedName

data class ListGeneralResponse(

    @field:SerializedName("pagination")
    val pagination: Pagination,

    @field:SerializedName("data")
    val data: ArrayList<DetailGeneralResponse?>

)