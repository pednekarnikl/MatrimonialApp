package com.ddesk.sddemoapp.presentation.di

import com.ddesk.sddemoapp.data.api.MatchesApiService
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesRemoteDataSource
import com.ddesk.sddemoapp.data.repository.dataSourceImpl.MatchesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMatchesRemoteDataSource(
        newsAPIService: MatchesApiService
    ):MatchesRemoteDataSource{
       return MatchesRemoteDataSourceImpl(newsAPIService)
    }

}












