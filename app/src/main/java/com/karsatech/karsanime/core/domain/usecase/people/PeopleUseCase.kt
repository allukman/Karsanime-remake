package com.karsatech.karsanime.core.domain.usecase.people

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.people.ListPeopleResponse
import kotlinx.coroutines.flow.Flow

interface PeopleUseCase {

    fun getTopPeople(): Flow<Resource<ListPeopleResponse>>

}