package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime (
    val animeId: String,
    val title: String,
    val image: String,
    val synopsis: String,
    val rating: String,
    val score: String,
    val rank: String,
    val members: String,
    val popularity: String,
    val favorites: String,
    val status: String,
    val episodes: String
): Parcelable