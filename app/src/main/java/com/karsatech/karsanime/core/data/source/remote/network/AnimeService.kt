package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.RandomAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): AnimeResponse

    @GET("random/anime")
    suspend fun getRandomAnime(): RandomAnimeResponse

    @GET("seasons/{year}/{season}")
    suspend fun getSeasonalAnime(
        @Path("year") year: String,
        @Path("season") season: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ) : AnimeResponse

    @GET("anime/{id}/full")
    suspend fun getFullDetailAnime(
        @Path("id") id: String
    ) : DetailAnimeResponse

}