package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class AnimeEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "animeId")
    var animeId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "synopsis")
    var synopsis: String,

)