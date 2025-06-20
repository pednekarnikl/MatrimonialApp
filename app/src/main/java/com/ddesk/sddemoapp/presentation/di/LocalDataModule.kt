package com.ddesk.sddemoapp.presentation.di

import com.ddesk.sddemoapp.data.db.SdDAO
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesLocalDataSource
import com.ddesk.sddemoapp.data.repository.dataSourceImpl.MatchesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideMatchesLocalDataSource(
        sdDAO: SdDAO
    ): MatchesLocalDataSource {
       return MatchesLocalDataSourceImpl(sdDAO)
    }

}








