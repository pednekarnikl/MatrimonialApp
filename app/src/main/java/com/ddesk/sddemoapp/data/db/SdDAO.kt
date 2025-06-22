package com.ddesk.sddemoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddesk.sddemoapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SdDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMatch(timelineEntity: UserEntity)

    //@Delete()
    @Query("delete from matches where sr =:id")
    suspend fun deleteUser(id: Int)

    @Query("select * from matches")
    fun getMatchesDB(): Flow<List<UserEntity>>

}