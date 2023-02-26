package com.gabrieal.carparkavailabilityapplication.di.components

import android.app.Application
import android.content.Context
import com.gabrieal.carparkavailabilityapplication.di.module.ActivityBuildersModule
import com.gabrieal.carparkavailabilityapplication.di.module.ApplicationModule
import com.gabrieal.carparkavailabilityapplication.di.module.RepositoryModule
import com.gabrieal.carparkavailabilityapplication.network.http.HttpClient
import com.gabrieal.carparkavailabilityapplication.views.base.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ApplicationModule::class,
        RepositoryModule::class,
        ActivityBuildersModule::class]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun build(): ApplicationComponent
    }

    fun inject(baseApplication: BaseApplication)
    fun getApplicationContext(): Context
    fun getHttpClient(): HttpClient
}