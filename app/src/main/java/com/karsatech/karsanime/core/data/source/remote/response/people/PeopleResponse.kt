package com.karsatech.karsanime.core.data.source.remote.response.people

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeopleResponse(

	@field:SerializedName("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("data")
	val data: List<DetailPeopleItem>
) : Parcelable

@Parcelize
data class Pagination(

	@field:SerializedName("has_next_page")
	val hasNextPage: Boolean? = null,

	@field:SerializedName("last_visible_page")
	val lastVisiblePage: Int? = null,

	@field:SerializedName("items")
	val items: Items? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
) : Parcelable

@Parcelize
data class Jpg(

	@field:SerializedName("image_url")
	val imageUrl: String? = null
) : Parcelable

@Parcelize
data class DetailPeopleItem(

	@field:SerializedName("alternate_names")
	val alternateNames: List<String?>? = null,

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("favorites")
	val favorites: Int? = null,

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("website_url")
	val websiteUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("given_name")
	val givenName: String? = null,

	@field:SerializedName("family_name")
	val familyName: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class Items(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class Images(

	@field:SerializedName("jpg")
	val jpg: Jpg? = null
) : Parcelable
