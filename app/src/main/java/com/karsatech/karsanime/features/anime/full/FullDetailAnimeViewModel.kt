package com.karsatech.karsanime.features.anime.full

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullDetailAnimeViewModel @Inject constructor(private val animeUseCase: AnimeUseCase, private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    fun getFullDetailAnime(id: String) = animeUseCase.getFullDetailAnime(id).asLiveData()

    fun getFavoriteByMalId(favId: String) = favoriteUseCase.getFavoriteAnimeById(favId).asLiveData()

    fun setFavorite(favAnime: Anime) = favoriteUseCase.setFavoriteAnime(favAnime)

    fun setUnFavorite(favId: String) = favoriteUseCase.removeFavoriteAnime(favId)

    fun getRecommendationAnime(id: String) = animeUseCase.getRecommendationAnime(id).asLiveData()

}