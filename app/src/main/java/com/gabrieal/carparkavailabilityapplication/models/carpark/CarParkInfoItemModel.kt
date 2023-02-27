package com.gabrieal.carparkavailabilityapplication.models.carpark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CarParkInfoItemModel (
    val total_lots: String?,
    val lot_type: String?,
    val lots_available: String?
) : Parcelable