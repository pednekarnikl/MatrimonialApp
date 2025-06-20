package com.ddesk.sddemoapp.data.repository.dataSource

import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import retrofit2.Response

interface MatchesRemoteDataSource {

    suspend fun getNewMatches():Response<NewMatchesApiResponse>
}