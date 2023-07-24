package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Manga (
    val mangaId: String,
    val title: String,
    val image: String,
    val chapters: String,
    val volumes: String,
    val status: String
): Parcelable