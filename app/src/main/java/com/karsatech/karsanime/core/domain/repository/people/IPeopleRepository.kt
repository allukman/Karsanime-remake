package com.karsatech.karsanime.core.domain.repository.people

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.data.source.remote.response.people.PeopleResponse
import kotlinx.coroutines.flow.Flow

interface IPeopleRepository {

    fun getTopPeople(): Flow<Resource<PeopleResponse>>

    fun getTopCharacters(): Flow<Resource<PeopleResponse>>

    fun getTopPeoplePagination(): LiveData<PagingData<DetailPeopleItem>>

    fun getTopCharactersPagination(): LiveData<PagingData<DetailPeopleItem>>

    fun getSearchPeoplePagination(query: String): LiveData<PagingData<DetailPeopleItem>>

    fun getSearchCharacterPagination(query: String): LiveData<PagingData<DetailPeopleItem>>

}