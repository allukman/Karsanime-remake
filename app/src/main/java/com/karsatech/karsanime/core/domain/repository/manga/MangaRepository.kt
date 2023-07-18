package com.karsatech.karsanime.core.domain.repository.manga

import android.util.Log
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.MangaService
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(private val mangaService: MangaService): IMangaRepository{

    override fun getTopManga(): Flow<Resource<ListGeneralResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = mangaService.getTopManga(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("MangaRepository", "getTopManga : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

}