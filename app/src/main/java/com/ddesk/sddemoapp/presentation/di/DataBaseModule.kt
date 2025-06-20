package com.ddesk.sddemoapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.ddesk.sddemoapp.data.db.AppRoomDatabase
import com.ddesk.sddemoapp.data.db.SdDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): AppRoomDatabase {
        return Room.databaseBuilder(app, AppRoomDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(appRoomDatabase: AppRoomDatabase): SdDAO {
        return appRoomDatabase.getArticleDAO()
    }


}
