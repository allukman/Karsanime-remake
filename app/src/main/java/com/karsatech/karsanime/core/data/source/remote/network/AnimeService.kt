package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.anime.ListGeneralResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : ListGeneralResponse

    @GET("top/anime")
    suspend fun getTopAnimePagination(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : ListGeneralResponse

    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : ListGeneralResponse

}