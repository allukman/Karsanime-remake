package com.karsatech.karsanime.core.domain.repository.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.AnimeService
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.paging.AnimePagingSource
import com.karsatech.karsanime.core.paging.UpcomingAnimePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(private val animeService: AnimeService):
    IAnimeRepository {

    override fun getTopAnime(): Flow<Resource<AnimeResponse>> {
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

    override fun getUpcomingAnime(): Flow<Resource<AnimeResponse>> {
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

    override fun getAnimeThisSeason(): Flow<Resource<AnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getAnimeThisSeason(1,10)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getAnimeThisSeason : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTopAnimePagination(): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                AnimePagingSource(animeService)
            }
        ).liveData
    }

    override fun getUpcomingAnimePagination(): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UpcomingAnimePagingSource(animeService)
            }
        ).liveData
    }

}