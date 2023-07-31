package com.karsatech.karsanime.features.anime.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeSeasonViewModel @Inject constructor(private val animeUseCase: AnimeUseCase): ViewModel() {

    fun getAnimeSeasonal(year: String, season: String) = animeUseCase.getAnimeSeasonalPagination(year, season).cachedIn(viewModelScope)

}