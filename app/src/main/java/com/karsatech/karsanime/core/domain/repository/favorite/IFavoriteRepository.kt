package com.karsatech.karsanime.core.domain.repository.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {

    fun getAllFavorite(): Flow<List<Anime>>

    fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>>

    fun setFavoriteAnime(favAnime: Anime)

    fun removeFavoriteAnime(favAnimeId: String)

}