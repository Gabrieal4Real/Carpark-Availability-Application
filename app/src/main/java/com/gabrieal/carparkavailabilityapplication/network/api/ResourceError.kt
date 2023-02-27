package com.gabrieal.carparkavailabilityapplication.network.api

import com.gabrieal.carparkavailabilityapplication.models.error.Error

class ResourceError {
    var errors: ArrayList<Error> = ArrayList()
    var error_description: String? = null

    override fun toString(): String {
        if (errors.isEmpty()) {
            return if (error_description.isNullOrEmpty().not()) {
                error_description!!
            } else {
                ""
            }
        }
        var errorText = ""
        for (e in errors) {
            if (errorText.isEmpty()) {
                errorText = e.message
            } else {
                errorText = errorText + ", " + e.message
            }
        }
        return errorText
    }
}