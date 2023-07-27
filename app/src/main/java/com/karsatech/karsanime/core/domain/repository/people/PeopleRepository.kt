package com.karsatech.karsanime.core.domain.repository.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.PeopleService
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.data.source.remote.response.people.PeopleResponse
import com.karsatech.karsanime.core.paging.CharactersPagingSource
import com.karsatech.karsanime.core.paging.PeoplePagingSource
import com.karsatech.karsanime.core.paging.SearchAnimePagingSource
import com.karsatech.karsanime.core.paging.SearchCharacterPagingSource
import com.karsatech.karsanime.core.paging.SearchPeoplePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(private val peopleService: PeopleService) : IPeopleRepository{

    override fun getTopPeople(): Flow<Resource<PeopleResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = peopleService.getTopPeople(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("PeopleRepository", "getTopPeople : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTopCharacters(): Flow<Resource<PeopleResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = peopleService.getTopCharacters(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("PeopleRepository", "getTopCharacter : $e")
            }
        }.flowOn(Dispatchers.IO)
    }


    override fun getTopPeoplePagination(): LiveData<PagingData<DetailPeopleItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PeoplePagingSource(peopleService)
            }
        ).liveData
    }

    override fun getTopCharactersPagination(): LiveData<PagingData<DetailPeopleItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                CharactersPagingSource(peopleService)
            }
        ).liveData
    }

    override fun getSearchPeoplePagination(query: String): LiveData<PagingData<DetailPeopleItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SearchPeoplePagingSource(peopleService, query)
            }
        ).liveData
    }

    override fun getSearchCharacterPagination(query: String): LiveData<PagingData<DetailPeopleItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SearchCharacterPagingSource(peopleService, query)
            }
        ).liveData
    }

}