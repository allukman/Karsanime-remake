package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_people")
data class PeopleEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "peopleId")
    var peopleId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "favorites")
    var favorites: String,

    )