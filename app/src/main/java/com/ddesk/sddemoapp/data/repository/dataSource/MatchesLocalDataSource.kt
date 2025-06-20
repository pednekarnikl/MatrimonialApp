package com.ddesk.sddemoapp.data.repository.dataSource

import com.ddesk.sddemoapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface MatchesLocalDataSource {

    suspend fun insertNewMatch(userEntity: UserEntity)
    suspend fun deleteUser(userEntity: UserEntity)
    fun getMatchLocalDB(): Flow<List<UserEntity>>

}