package com.karsatech.karsanime.core.domain.repository.people

import android.util.Log
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.PeopleService
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.ListPeopleResponse
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

}