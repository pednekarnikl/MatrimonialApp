package com.ddesk.sddemoapp.domain.repository

import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getNewMatches(): Resource<NewMatchesApiResponse>
    suspend fun saveMatchLocalDB(userEntity: UserEntity)
    suspend fun deleteMatchLocalDB(userEntity: UserEntity)
    fun getMatchLocalDB():Flow<List<UserEntity>>
}