package com.karsatech.karsanime.core.domain.usecase.people

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.data.source.remote.response.people.PeopleResponse
import kotlinx.coroutines.flow.Flow

interface PeopleUseCase {

    fun getTopPeople(): Flow<Resource<PeopleResponse>>

    fun getTopCharacters(): Flow<Resource<PeopleResponse>>

    fun getTopPeoplePagination() : LiveData<PagingData<DetailPeopleItem>>

    fun getTopCharactersPagination() : LiveData<PagingData<DetailPeopleItem>>

}