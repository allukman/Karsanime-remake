package com.karsatech.karsanime.features.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailAnimeViewModel @Inject constructor(private val favoriteUseCase: FavoriteUseCase): ViewModel() {

    fun getFavoriteByMalId(favId: String) = favoriteUseCase.getFavoriteAnimeById(favId).asLiveData()

    fun setFavorite(favAnime: Anime) = favoriteUseCase.setFavoriteAnime(favAnime)

    fun setUnFavorite(favId: String) = favoriteUseCase.removeFavoriteAnime(favId)

}