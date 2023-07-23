package com.karsatech.karsanime.core.domain.usecase.favorite

import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(private val favoriteRepository: FavoriteRepository): FavoriteUseCase {

    override fun getAllFavorite() = favoriteRepository.getAllFavorite()
    override fun getFavoriteAnimeById(favAnimeId: String) = favoriteRepository.getFavoriteAnimeById(favAnimeId)
    override fun setFavoriteAnime(favAnime: Anime) = favoriteRepository.setFavoriteAnime(favAnime)
    override fun removeFavoriteAnime(favAnimeId: String) = favoriteRepository.removeFavoriteAnime(favAnimeId)

}