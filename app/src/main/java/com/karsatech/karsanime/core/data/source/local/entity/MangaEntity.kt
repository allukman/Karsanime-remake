package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_manga")
data class MangaEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_favorite_manga")
    var idFavoriteManga: Int = 0,

    @ColumnInfo(name = "mangaId")
    var mangaId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "score")
    var score: String,

    @ColumnInfo(name = "ranking")
    var ranking: String,

    @ColumnInfo(name = "member")
    var member: String,

    @ColumnInfo(name = "popularity")
    var popularity: String,

    @ColumnInfo(name = "favorites")
    var favorites: String,

    @ColumnInfo(name = "synopsis")
    var synopsis: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "chapters")
    var chapters: String,

    @ColumnInfo(name = "volumes")
    var volumes: String

    )