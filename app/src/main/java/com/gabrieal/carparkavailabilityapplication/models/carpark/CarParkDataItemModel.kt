package com.gabrieal.carparkavailabilityapplication.models.carpark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CarParkDataItemModel (
    val carpark_info: List<CarParkInfoItemModel>?,
    val carpark_number: String?,
    val update_datetime: String?
) : Parcelable