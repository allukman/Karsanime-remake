package com.karsatech.karsanime.features.anime.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {

    val upcomingAnime = animeUseCase.getUpcomingAnime().asLiveData()

    val topAnime = animeUseCase.getTopAnime().asLiveData()

    fun getRandomAnime(randomInt: Int) = animeUseCase.getRandomAnime().asLiveData()

}