package com.gabrieal.carparkavailabilityapplication.di.module

import com.gabrieal.carparkavailabilityapplication.views.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
