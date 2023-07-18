package com.karsatech.karsanime.core.data.source.remote.response.people

import com.google.gson.annotations.SerializedName

data class DetailPeopleResponse(

    @field:SerializedName("mal_id")
    val malId: Int?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("website_url")
    val websiteUrl: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("given_name")
    val givenName: String?,

    @field:SerializedName("family_name")
    val familyName: String?,

    @field:SerializedName("alternate_names")
    val alternateNames: ArrayList<String?>,

    @field:SerializedName("birthday")
    val birthday: String?,

    @field:SerializedName("favorites")
    val favorites: Int?,

    @field:SerializedName("about")
    val about: String?,

    @field:SerializedName("images")
    val images: Images?,

    ) {
    data class Images(
        @field:SerializedName("jpg")
        val jpg: Jpg?,
    ) {
        data class Jpg(
            @field:SerializedName("image_url")
            val imageUrl: String?,
        )
    }
}