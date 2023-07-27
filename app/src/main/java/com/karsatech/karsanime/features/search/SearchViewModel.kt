package com.karsatech.karsanime.features.search

import androidx.lifecycle.ViewModel
import com.karsatech.karsanime.core.domain.usecase.anime.AnimeUseCase
import com.karsatech.karsanime.core.domain.usecase.manga.MangaUseCase
import com.karsatech.karsanime.core.domain.usecase.people.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val animeUseCase: AnimeUseCase, private val mangaUseCase: MangaUseCase, private val peopleUseCase: PeopleUseCase): ViewModel() {

    fun searchAnime(query: String) = animeUseCase.getSearchAnimePagination(query)

    fun searchManga(query: String) = mangaUseCase.getSearchMangaPagination(query)

    fun searchPeople(query: String) = peopleUseCase.getSearchPeoplePagination(query)

    fun searchCharacter(query: String) = peopleUseCase.getSearchCharacterPagination(query)

}