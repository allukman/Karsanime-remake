package com.karsatech.karsanime.core.data.source.remote.response.people

import com.google.gson.annotations.SerializedName
import com.karsatech.karsanime.core.data.source.remote.response.anime.Pagination

data class ListPeopleResponse(
    @field:SerializedName("pagination")
    val pagination: Pagination,

    @field:SerializedName("data")
    val data: List<DetailPeopleResponse>
)