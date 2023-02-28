package com.gabrieal.carparkavailabilityapplication.viewModels.carPark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.gabrieal.carparkavailabilityapplication.models.carpark.CarParkAvailabilityListModel
import com.gabrieal.carparkavailabilityapplication.models.carpark.CarParkDataItemModel
import com.gabrieal.carparkavailabilityapplication.network.api.Resource
import com.gabrieal.carparkavailabilityapplication.network.api.Resource.Status.*
import com.gabrieal.carparkavailabilityapplication.network.api.ResourceError
import com.gabrieal.carparkavailabilityapplication.repository.CarParkRepository
import com.gabrieal.carparkavailabilityapplication.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CarParkViewModelImpl @Inject constructor(private val carParkRepository: CarParkRepository) :
    ViewModel(), CarParkViewModel {

    private val carParkLiveData = MutableLiveData<List<CarParkDataItemModel>?>()
    private val timeStampLiveData = MutableLiveData<String?>()
    private val isLoading = MutableLiveData<Boolean>()
    private val isError = MutableLiveData<ResourceError?>()

    private val fetchCarParkAvailabilityObserver: Observer<Resource<CarParkAvailabilityListModel>> =
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

    private fun processMovieDetailsFetchResponse(response: Resource<CarParkAvailabilityListModel>?) {
        when (response?.status) {
            LOADING -> {
                isLoading.value = true
            }
            SUCCESS -> {
                isLoading.value = false
                carParkLiveData.value = response.data?.items?.get(0)?.carpark_data
                timeStampLiveData.value = response.data?.items?.get(0)?.timestamp?.let {
                    SimpleDateFormat(Constants.CONST_yyyyMMddHHmmSSXXX_FORMAT, Locale.US).parse(it)
                        ?.toLocaleString()
                }
            }
            ERROR  -> {
                isLoading.value = false
                isError.value = response.resourceError
            }
            else -> {}
        }
    }

    override fun observeCarParkAvailability(): LiveData<List<CarParkDataItemModel>?> {
        return carParkLiveData
    }

    override fun observeTimeStamp(): LiveData<String?> {
        return timeStampLiveData
    }
}