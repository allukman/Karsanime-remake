package com.karsatech.karsanime.core.di

import android.content.Context
import androidx.room.Room
import com.karsatech.karsanime.core.data.source.local.room.AnimeDao
import com.karsatech.karsanime.core.data.source.local.room.CharacterDao
import com.karsatech.karsanime.core.data.source.local.room.FavoriteDatabase
import com.karsatech.karsanime.core.data.source.local.room.MangaDao
import com.karsatech.karsanime.core.data.source.local.room.PeopleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteDatabase = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java, "Favorite.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideAnimeDao(database: FavoriteDatabase): AnimeDao = database.animeDao()

    @Provides
    fun provideMangaDao(database: FavoriteDatabase): MangaDao = database.mangaDao()

    @Provides
    fun providePeopleDao(database: FavoriteDatabase): PeopleDao = database.peopleDao()

    @Provides
    fun provideCharacterDao(database: FavoriteDatabase): CharacterDao = database.characterDao()
}