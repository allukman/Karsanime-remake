package com.karsatech.karsanime.core.domain.usecase.people

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.domain.repository.people.PeopleRepository
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val peopleRepository: PeopleRepository): PeopleUseCase {

    override fun getTopPeople() = peopleRepository.getTopPeople()

    override fun getTopCharacters() = peopleRepository.getTopCharacters()

    override fun getTopPeoplePagination() = peopleRepository.getTopPeoplePagination()

    override fun getTopCharactersPagination() = peopleRepository.getTopCharactersPagination()

    override fun getSearchPeoplePagination(query: String) = peopleRepository.getSearchPeoplePagination(query)

    override fun getSearchCharacterPagination(query: String) = peopleRepository.getSearchCharacterPagination(query)

}