package com.karsatech.karsanime.features.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(favoriteUseCase: FavoriteUseCase): ViewModel(){

    val favoriteAnime = favoriteUseCase.getAllFavoriteAnime().asLiveData()

    val favoriteManga = favoriteUseCase.getAllFavoriteManga().asLiveData()

    val favoritePeople = favoriteUseCase.getAllFavoritePeople().asLiveData()

}