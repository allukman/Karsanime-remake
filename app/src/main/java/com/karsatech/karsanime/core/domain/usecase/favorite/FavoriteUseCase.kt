package com.karsatech.karsanime.core.domain.usecase.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {

    fun getAllFavorite(): Flow<List<Anime>>
    fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>>
    fun setFavoriteAnime(favAnime: Anime)
    fun removeFavoriteAnime(favAnimeId: String)

}