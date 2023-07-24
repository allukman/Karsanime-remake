package com.karsatech.karsanime.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import com.karsatech.karsanime.core.data.source.local.entity.PeopleEntity

@Database(entities = [AnimeEntity::class, MangaEntity::class, PeopleEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun mangaDao(): MangaDao

    abstract fun peopleDao(): PeopleDao

}