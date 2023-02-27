package com.gabrieal.carparkavailabilityapplication.models.carpark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CarParkAvailabilityListModel (
    val items: List<CarParkAvailabilityItemModel>?
): Parcelable