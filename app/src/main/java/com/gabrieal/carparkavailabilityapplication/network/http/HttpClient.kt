package com.gabrieal.carparkavailabilityapplication.network.http

import com.gabrieal.carparkavailabilityapplication.network.api.NewAPIService
import retrofit2.Retrofit

interface HttpClient {
    fun getNewApiService(): NewAPIService
    fun getNewRetrofit(): Retrofit
}