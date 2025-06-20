package com.ddesk.sddemoapp.presentation.di

import android.app.Application
import com.ddesk.sddemoapp.domain.usecase.DeleteSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetNewMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.SaveMatchesUseCase
import com.ddesk.sddemoapp.presentation.viewmodel.MatchesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
  fun provideNewsViewModelFactory(
     application: Application,
     getNewMatchesUseCase: GetNewMatchesUseCase,
     saveMatchesUseCase: SaveMatchesUseCase,
     getSavedMatchesUseCase : GetSavedMatchesUseCase,
     deleteSavedMatchesUseCase: DeleteSavedMatchesUseCase
  ):MatchesViewModelFactory{
      return MatchesViewModelFactory(
          application,
          getNewMatchesUseCase,
          saveMatchesUseCase,
          getSavedMatchesUseCase,
          deleteSavedMatchesUseCase
      )
  }
}








