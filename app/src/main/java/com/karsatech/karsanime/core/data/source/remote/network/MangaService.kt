package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaService {

    @GET("top/manga")
    suspend fun getTopManga(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : AnimeResponse

}