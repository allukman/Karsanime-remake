package com.karsatech.karsanime.features.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import com.karsatech.karsanime.core.domain.usecase.manga.MangaUseCase
import com.karsatech.karsanime.core.domain.usecase.people.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListPaginationViewModel @Inject constructor(
    animeUseCase: AnimeUseCase,
    mangaUseCase: MangaUseCase,
    peopleUseCase: PeopleUseCase
) : ViewModel() {

    val topAnimePagination = animeUseCase.getTopAnimePagination().cachedIn(viewModelScope)

    val topMangaPagination = mangaUseCase.getTopMangaPagination().cachedIn(viewModelScope)

    val topPeoplePagination = peopleUseCase.getTopPeoplePagination().cachedIn(viewModelScope)

    val upcomingAnimePagination = animeUseCase.getUpcomingAnimePagination().cachedIn(viewModelScope)
}