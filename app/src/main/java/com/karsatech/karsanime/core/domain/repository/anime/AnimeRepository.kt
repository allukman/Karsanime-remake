package com.karsatech.karsanime.core.domain.repository.anime

import android.util.Log
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.AnimeService
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(private val animeService: AnimeService):
    IAnimeRepository {

    override fun getTopAnime(): Flow<Resource<ListGeneralResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getTopAnime(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getTopAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getUpcomingAnime(): Flow<Resource<ListGeneralResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getUpcomingAnime(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getUpcomingAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

}