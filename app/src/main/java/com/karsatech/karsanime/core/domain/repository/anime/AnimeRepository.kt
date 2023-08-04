package com.karsatech.karsanime.core.domain.repository.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.network.AnimeService
import com.karsatech.karsanime.core.data.source.remote.response.RecommendationAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeStatisticResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.PictureAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.RandomAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.ReviewAnimeResponse
import com.karsatech.karsanime.core.paging.AnimePagingSource
import com.karsatech.karsanime.core.paging.AnimeSeasonalPagingSource
import com.karsatech.karsanime.core.paging.SearchAnimePagingSource
import com.karsatech.karsanime.core.paging.ThisSeasonAnimePagingSource
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

    override fun getAnimeThisSeasonPagination(): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                ThisSeasonAnimePagingSource(animeService)
            }
        ).liveData
    }

    override fun getSearchAnimePagination(query: String): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                SearchAnimePagingSource(animeService, query)
            }
        ).liveData
    }

    override fun getRandomAnime(): Flow<Resource<RandomAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getRandomAnime()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getRandomAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeSeasonalPagination(year: String, season: String): LiveData<PagingData<DetailAnimeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                AnimeSeasonalPagingSource(animeService, year, season)
            }
        ).liveData
    }

    override fun getFullDetailAnime(id: String): Flow<Resource<DetailAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getFullDetailAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getFullDetailAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getRecommendationAnime(id: String): Flow<Resource<RecommendationAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getRecommendationAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getRecommendationAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getStatisticAnime(id: String): Flow<Resource<AnimeStatisticResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getStatisticAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getStatisticAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getCharacterAnime(id: String): Flow<Resource<CharacterAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getCharacterAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getCharacterAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getEpisodesAnime(id: String): Flow<Resource<EpisodesAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getEpisodesAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getEpisodesAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getNewsAnime(id: String): Flow<Resource<NewsAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getNewsAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getNewsAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getReviewAnime(id: String): Flow<Resource<ReviewAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getReviewsAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getReviewsAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPictureAnime(id: String): Flow<Resource<PictureAnimeResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = animeService.getPicturesAnime(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                Log.e("AnimeRepository", "getPicturesAnime : $e")
            }
        }.flowOn(Dispatchers.IO)
    }

}