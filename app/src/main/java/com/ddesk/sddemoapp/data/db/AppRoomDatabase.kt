package com.ddesk.sddemoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ddesk.sddemoapp.data.model.UserEntity



@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract  class AppRoomDatabase : RoomDatabase(){
    abstract fun getArticleDAO():SdDAO
}
