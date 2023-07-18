package com.karsatech.karsanime.core.domain.usecase.people

import com.karsatech.karsanime.core.domain.repository.people.PeopleRepository
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val peopleRepository: PeopleRepository): PeopleUseCase {

    override fun getTopPeople() = peopleRepository.getTopPeople()

}