package com.gabrieal.carparkavailabilityapplication.network.http

import com.gabrieal.carparkavailabilityapplication.BuildConfig
import com.gabrieal.carparkavailabilityapplication.network.api.NewAPIService
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseHttpClient : HttpClient {

    private var baseUrl = BuildConfig.BASE_URL
    private val okHttpClient: OkHttpClient
    private lateinit var newAPIService: NewAPIService
    private lateinit var newRetrofit: Retrofit

    init {
        okHttpClient = createOkHttpClient()
        createServices(okHttpClient)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        val retrofitDispatcher = Dispatcher()
        okHttpBuilder.dispatcher(retrofitDispatcher)
        okHttpBuilder.addInterceptor(loggingInterceptor)

        return okHttpBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun createServices(okHttpClient: OkHttpClient) {
        newAPIService = createNewAPIService(okHttpClient)
    }

    override fun getNewApiService(): NewAPIService {
        return newAPIService
    }


    override fun getNewRetrofit(): Retrofit {
        return newRetrofit
    }

    private fun createNewAPIService(client: OkHttpClient): NewAPIService {

        newRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return newRetrofit.create(NewAPIService::class.java)

    }
}