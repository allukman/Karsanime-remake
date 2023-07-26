package com.karsatech.karsanime.core.domain.repository.manga

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import kotlinx.coroutines.flow.Flow

interface IMangaRepository {

    fun getTopManga(): Flow<Resource<AnimeResponse>>

    fun getTopMangaPagination(): LiveData<PagingData<DetailAnimeItem>>

}