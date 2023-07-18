package com.karsatech.karsanime.core.data.source.remote.network

import com.karsatech.karsanime.core.data.source.remote.response.people.ListPeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("top/people")
    suspend fun getTopPeople(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : ListPeopleResponse

}