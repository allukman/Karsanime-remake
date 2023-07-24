package com.karsatech.karsanime.core.domain.usecase.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository): FavoriteUseCase {

    override fun getAllFavoriteAnime() = favoriteRepository.getAllFavoriteAnime()
    override fun getFavoriteAnimeById(favAnimeId: String) = favoriteRepository.getFavoriteAnimeById(favAnimeId)
    override fun setFavoriteAnime(favAnime: Anime) = favoriteRepository.setFavoriteAnime(favAnime)
    override fun removeFavoriteAnime(favAnimeId: String) = favoriteRepository.removeFavoriteAnime(favAnimeId)

    override fun getAllFavoriteManga() = favoriteRepository.getAllFavoriteManga()
    override fun getFavoriteMangaById(favMangaId: String) = favoriteRepository.getFavoriteMangaById(favMangaId)
    override fun setFavoriteManga(favManga: Manga) = favoriteRepository.setFavoriteManga(favManga)
    override fun removeFavoriteManga(favMangaId: String) = favoriteRepository.removeFavoriteManga(favMangaId)

}