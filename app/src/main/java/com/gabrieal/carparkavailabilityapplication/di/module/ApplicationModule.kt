package com.gabrieal.carparkavailabilityapplication.di.module

import android.content.Context
import com.gabrieal.carparkavailabilityapplication.network.http.BaseHttpClient
import com.gabrieal.carparkavailabilityapplication.network.http.HttpClient
import com.gabrieal.carparkavailabilityapplication.views.base.BaseApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class ApplicationModule(private val baseApplication: BaseApplication) {

    @Provides
    @Singleton
    fun application(): BaseApplication {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(baseApplication: BaseApplication): Context {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return BaseHttpClient()
    }

}