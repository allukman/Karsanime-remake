package com.karsatech.karsanime.core.domain.usecase.anime

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.flow.Flow

interface AnimeUseCase {

    fun getTopAnime(): Flow<Resource<ListGeneralResponse>>

    fun getUpcomingAnime(): Flow<Resource<ListGeneralResponse>>
}