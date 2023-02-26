package com.gabrieal.carparkavailabilityapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrieal.carparkavailabilityapplication.di.ViewModelKey
import com.gabrieal.carparkavailabilityapplication.di.factory.ViewModelFactory
import com.gabrieal.carparkavailabilityapplication.viewModels.carPark.CarParkViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CarParkViewModelImpl::class)
    abstract fun bindCarParkAvailabilityImpl(carParkViewModelImpl: CarParkViewModelImpl): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
