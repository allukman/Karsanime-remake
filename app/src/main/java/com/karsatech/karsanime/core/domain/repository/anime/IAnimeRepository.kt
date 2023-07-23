package com.karsatech.karsanime.core.domain.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {

    fun getTopAnime(): Flow<Resource<ListGeneralResponse>>

    fun getUpcomingAnime(): Flow<Resource<ListGeneralResponse>>

    fun getTopAnimePagination(): LiveData<PagingData<DetailGeneralResponse>>

    fun getUpcomingAnimePagination(): LiveData<PagingData<DetailGeneralResponse>>

}