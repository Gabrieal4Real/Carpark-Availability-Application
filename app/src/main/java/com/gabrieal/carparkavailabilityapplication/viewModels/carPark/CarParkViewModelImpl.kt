package com.gabrieal.carparkavailabilityapplication.viewModels.carPark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.gabrieal.carparkavailabilityapplication.network.api.Resource
import com.gabrieal.carparkavailabilityapplication.network.api.Resource.Status.*
import com.gabrieal.carparkavailabilityapplication.network.api.ResourceError
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepository
import javax.inject.Inject

class CarParkViewModelImpl @Inject constructor(private val carParkRepository: CarParkRepository) :
    ViewModel(), CarParkViewModel {

    private val carParkLiveData = MutableLiveData<Any?>()
    private val isLoading = MutableLiveData<Boolean>()
    private val isError = MutableLiveData<ResourceError?>()

    private val fetchCarParkAvailabilityObserver: Observer<Resource<Any>> =
        Observer { t -> processMovieDetailsFetchResponse(t) }

    override fun getCarParkAvailability(dateTime: String) {
        carParkRepository.getCarParkListFromAPI(dateTime)
            .observeForever { fetchCarParkAvailabilityObserver.onChanged(it) }
    }

    override fun observeLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun observeError(): LiveData<ResourceError?> {
        return isError
    }

    private fun processMovieDetailsFetchResponse(response: Resource<Any>?) {
        when (response?.status) {
            LOADING -> {
                isLoading.value = true
            }
            SUCCESS -> {
                isLoading.value = false
                carParkLiveData.value = response.data
            }
            ERROR -> {
                isLoading.value = false
                isError.value = response.resourceError
            }
            null -> {
            }
        }
    }

    override fun observeCarParkAvailability(): LiveData<Any?> {
        return carParkLiveData
    }
}