package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.RecommendationAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeStatisticResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsAnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.RandomAnimeResponse
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

    @GET("anime/{id}/recommendations")
    suspend fun getRecommendationAnime(
        @Path("id") id: String
    ) : RecommendationAnimeResponse

    @GET("anime/{id}/statistics")
    suspend fun getStatisticAnime(
        @Path("id") id: String
    ) : AnimeStatisticResponse

    @GET("anime/{id}/characters")
    suspend fun getCharacterAnime(
        @Path("id") id: String
    ) : CharacterAnimeResponse

    @GET("anime/{id}/episodes")
    suspend fun getEpisodesAnime(
        @Path("id") id: String
    ) : EpisodesAnimeResponse

    @GET("anime/{id}/news")
    suspend fun getNewsAnime(
        @Path("id") id: String
    ) : NewsAnimeResponse
}