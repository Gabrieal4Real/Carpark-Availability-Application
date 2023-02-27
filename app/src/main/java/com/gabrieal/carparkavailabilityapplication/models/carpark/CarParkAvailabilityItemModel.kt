package com.gabrieal.carparkavailabilityapplication.models.carpark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CarParkAvailabilityItemModel (
    val timestamp: String?,
    val carpark_data: List<CarParkDataItemModel>?
) : Parcelable