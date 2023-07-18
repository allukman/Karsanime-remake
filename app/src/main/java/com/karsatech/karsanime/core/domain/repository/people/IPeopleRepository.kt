package com.karsatech.karsanime.core.domain.repository.people

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.people.ListPeopleResponse
import kotlinx.coroutines.flow.Flow

interface IPeopleRepository {

    fun getTopPeople(): Flow<Resource<ListPeopleResponse>>

}