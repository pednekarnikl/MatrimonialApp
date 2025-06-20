package com.ddesk.sddemoapp.presentation.di

import com.ddesk.sddemoapp.domain.repository.MatchesRepository
import com.ddesk.sddemoapp.domain.usecase.DeleteSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetNewMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.SaveMatchesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
   @Singleton
   @Provides
   fun provideGetNewMatchesUseCase(
      matchesRepository: MatchesRepository
   ): GetNewMatchesUseCase {
      return GetNewMatchesUseCase(matchesRepository)
   }


   @Singleton
   @Provides
   fun provideSaveMatchesUseCase(
      matchesRepository: MatchesRepository
   ): SaveMatchesUseCase {
      return SaveMatchesUseCase(matchesRepository)
   }

   @Singleton
   @Provides
   fun provideGetSavedMatchesUseCase(
      matchesRepository: MatchesRepository
   ): GetSavedMatchesUseCase {
      return GetSavedMatchesUseCase(matchesRepository)
   }

   @Singleton
   @Provides
   fun provideDeleteSavedMatchesUseCase(
      matchesRepository: MatchesRepository
   ): DeleteSavedMatchesUseCase {
      return DeleteSavedMatchesUseCase(matchesRepository)
   }
}