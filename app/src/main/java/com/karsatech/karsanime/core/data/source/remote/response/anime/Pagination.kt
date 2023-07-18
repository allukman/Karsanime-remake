package com.karsatech.karsanime.core.data.source.remote.response.anime

import com.google.gson.annotations.SerializedName

data class Pagination (

    @field:SerializedName("last_visible_page")
    val lastVisiblePage: Int?,

    @field:SerializedName("has_next_page")
    val hasNextPage: Boolean?,

    @field:SerializedName("current_page")
    val currentPage: Int?,

    @field:SerializedName("items")
    val items: ItemsPagination?,

) {
    data class ItemsPagination(

        @field:SerializedName("count")
        val count: Int?,

        @field:SerializedName("total")
        val total: Int?,

        @field:SerializedName("per_page")
        val perPage: Int?,
    )
}