package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_manga")
data class MangaEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mangaId")
    var mangaId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "chapters")
    var chapters: String,

    @ColumnInfo(name = "volumes")
    var volumes: String,

    @ColumnInfo(name = "status")
    var status: String,

    )