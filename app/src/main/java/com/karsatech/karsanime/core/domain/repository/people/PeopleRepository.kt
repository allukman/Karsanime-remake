package com.karsatech.karsanime.core.domain.repository.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.PeopleService
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.ListPeopleResponse
import com.karsatech.karsanime.core.paging.AnimePagingSource
import com.karsatech.karsanime.core.paging.PeoplePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(private val peopleService: PeopleService) : IPeopleRepository{

    override fun getTopPeople(): Flow<Resource<ListPeopleResponse>> {
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

    override fun getTopPeoplePagination(): LiveData<PagingData<DetailPeopleResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PeoplePagingSource(peopleService)
            }
        ).liveData
    }

}