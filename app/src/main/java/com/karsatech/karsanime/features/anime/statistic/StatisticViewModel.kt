package com.karsatech.karsanime.features.anime.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class StatisticViewModel @Inject constructor(private val animeUseCase: AnimeUseCase): ViewModel() {

    fun getStatisticAnime(id: String) = animeUseCase.getStatisticAnime(id).asLiveData()

}