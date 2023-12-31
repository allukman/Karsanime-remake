package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_people")
data class PeopleEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_favorite_people")
    var idFavoritePeople: Int = 0,

    @ColumnInfo(name = "peopleId")
    var peopleId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "birthday")
    var birthday: String,

    @ColumnInfo(name = "favorites")
    var favorites: String,

    @ColumnInfo(name = "about")
    var about: String

    )