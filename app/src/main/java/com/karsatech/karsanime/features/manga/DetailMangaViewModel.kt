package com.karsatech.karsanime.features.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMangaViewModel @Inject constructor(private val favoriteUseCase: FavoriteUseCase): ViewModel() {

    fun getFavoriteMangaByMalId(favId: String) = favoriteUseCase.getFavoriteMangaById(favId).asLiveData()

    fun setFavoriteManga(favManga: Manga) = favoriteUseCase.setFavoriteManga(favManga)

    fun setUnFavoriteManga(favId: String) = favoriteUseCase.removeFavoriteManga(favId)

}