package com.karsatech.karsanime.features.anime.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(private val animeUseCase: AnimeUseCase): ViewModel() {

    fun getEpisodesAnime(id: String) = animeUseCase.getEpisodesAnime(id).asLiveData()
}