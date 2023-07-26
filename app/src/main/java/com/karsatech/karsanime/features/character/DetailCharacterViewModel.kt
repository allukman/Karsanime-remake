package com.karsatech.karsanime.features.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.model.Character
import com.karsatech.karsanime.core.domain.usecase.favorite.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(private val favoriteUseCase: FavoriteUseCase): ViewModel() {

    fun getFavoriteCharacterByMalId(favId: String) = favoriteUseCase.getFavoriteCharacterById(favId).asLiveData()

    fun setFavoriteCharacter(favCharacter: Character) = favoriteUseCase.setFavoriteCharacter(favCharacter)

    fun setUnFavoriteCharacter(favId: String) = favoriteUseCase.removeFavoriteCharacter(favId)

}