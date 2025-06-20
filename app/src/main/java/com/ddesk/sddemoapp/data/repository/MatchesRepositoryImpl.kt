package com.ddesk.sddemoapp.data.repository

import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesLocalDataSource
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesRemoteDataSource
import com.ddesk.sddemoapp.data.util.Resource
import com.ddesk.sddemoapp.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MatchesRepositoryImpl(
    private val matchesRemoteDataSource: MatchesRemoteDataSource,
    private val matchesLocalDataSource: MatchesLocalDataSource,

    ) : MatchesRepository {
    override suspend fun getNewMatches(): Resource<NewMatchesApiResponse> {
        return responseToResult(matchesRemoteDataSource.getNewMatches())
    }

    private fun responseToResult(response:Response<NewMatchesApiResponse>):Resource<NewMatchesApiResponse>{
         if (response.isSuccessful) response.body()?.let { return Resource.Success(it) }
        return Resource.Error(response.message())

    }

    override suspend fun saveMatchLocalDB(userEntity: UserEntity) {
        matchesLocalDataSource.insertNewMatch(userEntity)
    }

    override suspend fun deleteMatchLocalDB(userEntity: UserEntity) {
        matchesLocalDataSource.deleteUser(userEntity)
    }

    override fun getMatchLocalDB(): Flow<List<UserEntity>> {
       return matchesLocalDataSource.getMatchLocalDB()
    }

}