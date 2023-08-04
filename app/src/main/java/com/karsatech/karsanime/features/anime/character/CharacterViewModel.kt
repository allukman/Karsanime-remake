package com.karsatech.karsanime.features.anime.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {

    fun getCharacterAnime(id: String) = animeUseCase.getCharacterAnime(id).asLiveData()

}