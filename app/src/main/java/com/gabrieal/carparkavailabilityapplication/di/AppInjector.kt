package com.gabrieal.carparkavailabilityapplication.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.gabrieal.carparkavailabilityapplication.di.components.DaggerApplicationComponent
import com.gabrieal.carparkavailabilityapplication.di.module.ApplicationModule
import com.gabrieal.carparkavailabilityapplication.views.base.BaseApplication
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector {
    fun init(baseApplication: BaseApplication) {
        DaggerApplicationComponent.builder().application(baseApplication)
            .applicationModule(ApplicationModule(baseApplication))
            .build().inject(baseApplication)
        baseApplication
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityResumed(activity: Activity) {
                }

                override fun onActivityPaused(activity: Activity) {
                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                }

                override fun onActivityDestroyed(activity: Activity) {
                }
            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is Injectable || activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is androidx.fragment.app.FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: androidx.fragment.app.FragmentManager,
                            f: androidx.fragment.app.Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}
