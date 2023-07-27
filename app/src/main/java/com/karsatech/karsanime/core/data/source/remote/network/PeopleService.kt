package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("top/people")
    suspend fun getTopPeople(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : PeopleResponse

    @GET("top/characters")
    suspend fun getTopCharacters(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : PeopleResponse

    @GET("people")
    suspend fun searchPeople(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): PeopleResponse

    @GET("characters")
    suspend fun searchCharacter(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): PeopleResponse

}