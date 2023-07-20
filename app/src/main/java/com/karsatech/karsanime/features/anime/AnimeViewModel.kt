package com.karsatech.karsanime.features.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(animeUseCase: AnimeUseCase) : ViewModel() {

    val topAnimePagination = animeUseCase.getTopAnimePagination().cachedIn(viewModelScope)

}