package com.ddesk.sddemoapp.data.repository.dataSourceImpl

import com.ddesk.sddemoapp.data.api.MatchesApiService
import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesRemoteDataSource
import retrofit2.Response

class MatchesRemoteDataSourceImpl(
    private val apiService: MatchesApiService
): MatchesRemoteDataSource {
    override suspend fun getNewMatches(): Response<NewMatchesApiResponse> {
        return apiService.getMatches()
    }

}