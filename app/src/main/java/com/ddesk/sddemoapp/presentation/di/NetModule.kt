package com.ddesk.sddemoapp.presentation.di


import com.ddesk.sddemoapp.data.api.MatchesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
         return Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl("https://randomuser.me")
             .build()
    }

    @Singleton
    @Provides
    fun provideMatchesApiService(retrofit: Retrofit):MatchesApiService{
        return retrofit.create(MatchesApiService::class.java)
    }

}













