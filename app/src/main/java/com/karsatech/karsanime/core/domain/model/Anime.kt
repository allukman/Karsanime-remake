package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime (
    val animeId: String,
    val title: String,
    val image: String,
    val synopsis: String
): Parcelable