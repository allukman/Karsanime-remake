package com.karsatech.karsanime.features.anime.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val animeUseCase: AnimeUseCase): ViewModel() {

    fun getNewsAnime(id: String) = animeUseCase.getNewsAnime(id).asLiveData()

}