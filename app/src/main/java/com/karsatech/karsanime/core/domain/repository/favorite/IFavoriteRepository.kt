package com.karsatech.karsanime.core.domain.repository.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {

    fun getAllFavoriteAnime(): Flow<List<Anime>>
    fun getFavoriteAnimeById(favAnimeId: String): Flow<List<Anime>>
    fun setFavoriteAnime(favAnime: Anime)
    fun removeFavoriteAnime(favAnimeId: String)

    fun getAllFavoriteManga(): Flow<List<Manga>>
    fun getFavoriteMangaById(favMangaId: String): Flow<List<Manga>>
    fun setFavoriteManga(favManga: Manga)
    fun removeFavoriteManga(favMangaId: String)

}