package com.karsatech.karsanime.core.domain.usecase.manga

import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import kotlinx.coroutines.flow.Flow

interface MangaUseCase {
    fun getTopManga(): Flow<Resource<ListGeneralResponse>>
}