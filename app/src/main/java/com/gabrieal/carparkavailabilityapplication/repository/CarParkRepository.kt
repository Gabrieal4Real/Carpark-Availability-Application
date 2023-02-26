package com.gabrieal.carparkavailabilityapplication.repository

import androidx.lifecycle.LiveData
import com.gabrieal.carparkavailabilityapplication.network.api.Resource

interface CarParkRepository {
    fun getCarParkListFromAPI(dateTime: String): LiveData<Resource<Any>>
}
