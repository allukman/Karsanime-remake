package com.karsatech.karsanime.ui.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(animeUseCase: AnimeUseCase) : ViewModel() {

    val topAnime = animeUseCase.getTopAnime().asLiveData()

}