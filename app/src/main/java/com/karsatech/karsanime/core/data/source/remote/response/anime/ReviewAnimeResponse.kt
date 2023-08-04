package com.karsatech.karsanime.core.data.source.remote.response.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewAnimeResponse(

	@field:SerializedName("data")
	val data: List<ReviewItem>? = null
) : Parcelable

@Parcelize
data class UserReview(

	@field:SerializedName("images")
	val images: ImagesReview? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class Reactions(

	@field:SerializedName("love_it")
	val loveIt: Int? = null,

	@field:SerializedName("well_written")
	val wellWritten: Int? = null,

	@field:SerializedName("overall")
	val overall: Int? = null,

	@field:SerializedName("informative")
	val informative: Int? = null,

	@field:SerializedName("confusing")
	val confusing: Int? = null,

	@field:SerializedName("nice")
	val nice: Int? = null,

	@field:SerializedName("funny")
	val funny: Int? = null,

	@field:SerializedName("creative")
	val creative: Int? = null
) : Parcelable

@Parcelize
data class JpgReview(

	@field:SerializedName("image_url")
	val imageUrl: String? = null
) : Parcelable


@Parcelize
data class ImagesReview(

	@field:SerializedName("jpg")
	val jpg: JpgReview? = null,

) : Parcelable

@Parcelize
data class ReviewItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("is_spoiler")
	val isSpoiler: Boolean? = null,

	@field:SerializedName("reactions")
	val reactions: Reactions? = null,

	@field:SerializedName("mal_id")
	val malId: Int? = null,

	@field:SerializedName("is_preliminary")
	val isPreliminary: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("user")
	val user: UserReview? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	var isExpanded: Boolean = false
) : Parcelable
