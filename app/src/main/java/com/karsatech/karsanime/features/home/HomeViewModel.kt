package com.karsatech.karsanime.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import com.karsatech.karsanime.core.domain.usecase.manga.MangaUseCase
import com.karsatech.karsanime.core.domain.usecase.people.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(animeUseCase: AnimeUseCase, mangaUseCase: MangaUseCase, peopleUseCase: PeopleUseCase) : ViewModel() {

    val upcomingAnime = animeUseCase.getUpcomingAnime().asLiveData()

    val topAnime = animeUseCase.getTopAnime().asLiveData()

    val topManga = mangaUseCase.getTopManga().asLiveData()

    val topPeople = peopleUseCase.getTopPeople().asLiveData()
}