package com.ddesk.sddemoapp.data.api

import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApiService {

    @GET("api")//?results=10
    suspend fun getMatches(
        @Query("results")
        count:Int?=10
    ): Response<NewMatchesApiResponse>
}