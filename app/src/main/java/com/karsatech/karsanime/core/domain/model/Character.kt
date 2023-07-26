package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character (
    val characterId: String,
    val name: String,
    val image: String,
    val birthday: String,
    val favorites: String,
    val about: String
): Parcelable