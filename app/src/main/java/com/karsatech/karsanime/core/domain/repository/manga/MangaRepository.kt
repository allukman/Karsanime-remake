package com.karsatech.karsanime.core.domain.repository.manga

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.MangaService
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.paging.MangaPagingSource
import com.karsatech.karsanime.core.paging.SearchAnimePagingSource
import com.karsatech.karsanime.core.paging.SearchMangaPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(private val mangaService: MangaService): IMangaRepository{

    override fun getTopManga(): Flow<Resource<AnimeResponse>> {
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

    override fun getTopMangaPagination(): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                MangaPagingSource(mangaService)
            }
        ).liveData
    }

    override fun getSearchMangaPagination(query: String): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SearchMangaPagingSource(mangaService, query)
            }
        ).liveData
    }
}