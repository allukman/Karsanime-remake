package com.karsatech.karsanime.features.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.manga.MangaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(mangaUseCase: MangaUseCase): ViewModel() {

    val topMangaPagination = mangaUseCase.getTopMangaPagination().cachedIn(viewModelScope)

}