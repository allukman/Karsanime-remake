package com.karsatech.karsanime.features.anime.pictures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(private val animeUseCase: AnimeUseCase): ViewModel() {

    fun getPicturesAnime(id: String) = animeUseCase.getPicturesAnime(id).asLiveData()

}