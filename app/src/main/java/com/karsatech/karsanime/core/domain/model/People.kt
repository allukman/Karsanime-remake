package com.karsatech.karsanime.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class People (
    val peopleId: String,
    val name: String,
    val image: String,
    val favorites: String
): Parcelable