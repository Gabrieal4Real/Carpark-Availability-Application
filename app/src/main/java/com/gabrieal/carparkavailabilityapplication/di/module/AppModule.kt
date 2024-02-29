package com.gabrieal.carparkavailabilityapplication.di.module

import com.gabrieal.carparkavailabilityapplication.network.http.BaseHttpClient
import com.gabrieal.carparkavailabilityapplication.network.http.HttpClient
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepository
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepositoryImpl
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { BaseHttpClient() }
    single<CarParkRepository> { CarParkRepositoryImpl(get()) }
    viewModel { CarParkViewModelImpl(get()) }
}
