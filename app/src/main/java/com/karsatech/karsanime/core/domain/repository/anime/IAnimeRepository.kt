package com.karsatech.karsanime.core.domain.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.RecommendationAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeStatisticResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.RandomAnimeResponse
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {

    fun getTopAnime(): Flow<Resource<AnimeResponse>>

    fun getUpcomingAnime(): Flow<Resource<AnimeResponse>>

    fun getAnimeThisSeason(): Flow<Resource<AnimeResponse>>

    fun getTopAnimePagination(): LiveData<PagingData<DetailAnimeItem>>

    fun getUpcomingAnimePagination(): LiveData<PagingData<DetailAnimeItem>>

    fun getAnimeThisSeasonPagination(): LiveData<PagingData<DetailAnimeItem>>

    fun getSearchAnimePagination(query: String): LiveData<PagingData<DetailAnimeItem>>

    fun getRandomAnime(): Flow<Resource<RandomAnimeResponse>>

    fun getAnimeSeasonalPagination(year: String, season: String): LiveData<PagingData<DetailAnimeItem>>

    fun getFullDetailAnime(id: String): Flow<Resource<DetailAnimeResponse>>

    fun getRecommendationAnime(id: String): Flow<Resource<RecommendationAnimeResponse>>

    fun getStatisticAnime(id: String): Flow<Resource<AnimeStatisticResponse>>

    fun getCharacterAnime(id: String): Flow<Resource<CharacterAnimeResponse>>

    fun getEpisodesAnime(id: String): Flow<Resource<EpisodesAnimeResponse>>

    fun getNewsAnime(id: String): Flow<Resource<NewsAnimeResponse>>

}