package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class AnimeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_favorite_anime")
    var idFavoriteAnime: Int = 0,

    @ColumnInfo(name = "animeId")
    var animeId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "synopsis")
    var synopsis: String,

    @ColumnInfo(name = "rating")
    var rating: String,

    @ColumnInfo(name = "score")
    var score: String,

    @ColumnInfo(name = "rank")
    var rank: String,

    @ColumnInfo(name = "members")
    var members: String,

    @ColumnInfo(name = "popularity")
    var popularity: String,

    @ColumnInfo(name = "favorites")
    var favorites: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "episodes")
    var episodes: String

)