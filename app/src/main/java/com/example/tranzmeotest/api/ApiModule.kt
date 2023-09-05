package com.example.tranzmeotest.api

import com.example.tranzmeotest.api.ApiClient.getServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): ApiService {
        return getServiceInstance()
    }
}