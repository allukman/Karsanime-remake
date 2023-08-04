package com.karsatech.karsanime.features.anime.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {

    fun getReviewAnime(id: String) = animeUseCase.getReviewAnime(id).asLiveData()

}