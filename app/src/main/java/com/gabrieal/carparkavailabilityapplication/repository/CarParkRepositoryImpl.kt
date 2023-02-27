package com.gabrieal.carparkavailabilityapplication.repository

import androidx.lifecycle.LiveData
import com.gabrieal.carparkavailabilityapplication.models.carpark.CarParkAvailabilityListModel
import com.gabrieal.carparkavailabilityapplication.network.api.NetworkCall
import com.gabrieal.carparkavailabilityapplication.network.api.NewAPIService
import com.gabrieal.carparkavailabilityapplication.network.api.Resource
import com.gabrieal.carparkavailabilityapplication.network.http.HttpClient

class CarParkRepositoryImpl(private val httpClient: HttpClient) : CarParkRepository {
    private val carParkCall = NetworkCall<CarParkAvailabilityListModel>()
    private fun getApiService(): NewAPIService {
        return httpClient.getNewApiService()
    }

    override fun getCarParkListFromAPI(dateTime: String): LiveData<Resource<CarParkAvailabilityListModel>> {
        return carParkCall.makeCall(getApiService().getAvailableParking(dateTime))
    }

}
