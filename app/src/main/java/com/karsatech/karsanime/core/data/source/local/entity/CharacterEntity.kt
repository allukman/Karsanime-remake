package com.karsatech.karsanime.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_character")
data class CharacterEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_favorite_character")
    var idFavoriteCharacter: Int = 0,

    @ColumnInfo(name = "characterId")
    var characterId: String,

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