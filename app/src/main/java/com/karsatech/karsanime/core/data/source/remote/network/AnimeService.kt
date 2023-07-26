package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : AnimeResponse

    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : AnimeResponse

    @GET("seasons/now")
    suspend fun getAnimeThisSeason(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : AnimeResponse

}