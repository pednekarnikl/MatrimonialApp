package com.ddesk.sddemoapp.presentation.di


import com.ddesk.sddemoapp.data.repository.MatchesRepositoryImpl
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesLocalDataSource
import com.ddesk.sddemoapp.data.repository.dataSource.MatchesRemoteDataSource
import com.ddesk.sddemoapp.domain.repository.MatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        matchesRemoteDataSource: MatchesRemoteDataSource,
        matchesLocalDataSource: MatchesLocalDataSource
    ):MatchesRepository{
        return MatchesRepositoryImpl(matchesRemoteDataSource,matchesLocalDataSource)
    }

}














