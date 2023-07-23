package com.karsatech.karsanime.core.data.source.local

import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import com.karsatech.karsanime.core.data.source.local.entity.TourismEntity
import com.karsatech.karsanime.core.data.source.local.room.AnimeDao
import com.karsatech.karsanime.core.data.source.local.room.TourismDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val animeDao: AnimeDao) {

    fun getAllFavoriteAnime(): Flow<List<AnimeEntity>> = animeDao.getAllFavoriteAnime()

    fun getFavoriteAnimeById(animeId: String): Flow<List<AnimeEntity>> = animeDao.getFavoriteById(animeId)

    fun setFavoriteAnime(anime: AnimeEntity) {
        animeDao.insertFavoriteAnime(anime)
    }

    fun removeFavoriteAnime(animeId: String) {
        animeDao.removeFavoriteAnime(animeId)
    }
}

