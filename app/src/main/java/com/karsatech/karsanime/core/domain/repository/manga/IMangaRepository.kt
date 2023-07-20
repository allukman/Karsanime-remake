package com.karsatech.karsanime.core.domain.repository.manga

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.flow.Flow

interface IMangaRepository {

    fun getTopManga(): Flow<Resource<ListGeneralResponse>>

    fun getTopMangaPagination(): LiveData<PagingData<DetailGeneralResponse>>

}