package com.karsatech.karsanime.core.domain.usecase.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import kotlinx.coroutines.flow.Flow

interface AnimeUseCase {
    fun getTopAnime(): Flow<Resource<AnimeResponse>>
    fun getUpcomingAnime(): Flow<Resource<AnimeResponse>>

    fun getAnimeThisSeason(): Flow<Resource<AnimeResponse>>
    fun getTopAnimePagination() : LiveData<PagingData<DetailAnimeItem>>
    fun getUpcomingAnimePagination() : LiveData<PagingData<DetailAnimeItem>>
}