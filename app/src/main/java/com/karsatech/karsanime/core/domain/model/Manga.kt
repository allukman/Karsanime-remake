package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Manga (
    val mangaId: String,
    val title: String,
    val image: String,
    val score: String,
    val rank: String,
    val members: String,
    val popularity: String,
    val favorites: String,
    val synopsis: String,
    val status: String,
    val chapters: String,
    val volumes: String
): Parcelable