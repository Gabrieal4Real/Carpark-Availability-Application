package com.gabrieal.carparkavailabilityapplication.di.module

import com.gabrieal.carparkavailabilityapplication.network.http.HttpClient
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepository
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCarParkRepository(httpClient: HttpClient): CarParkRepository {
        return CarParkRepositoryImpl(httpClient)
    }
}
