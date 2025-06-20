package com.ddesk.sddemoapp.data.repository.dataSourceImpl

import com.ddesk.sddemoapp.data.db.SdDAO
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesLocalDataSource
import kotlinx.coroutines.flow.Flow

class MatchesLocalDataSourceImpl(
    private val dao: SdDAO
): MatchesLocalDataSource {


    override suspend fun insertNewMatch(userEntity: UserEntity) {
        dao.insertNewMatch(userEntity)
    }

    override suspend fun deleteUser(userEntity: UserEntity) {
        dao.deleteUser(userEntity.sr?:0)
    }

    override fun getMatchLocalDB(): Flow<List<UserEntity>> {
        return dao.getMatchesDB()
    }


}