package com.hodhod.hodohodadmin.dto

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.hodhod.hodohodadmin.R

/**
 * Created by Eslam Hussein on 8/3/18.
 */
data class ServiceProviderItem(val type: String, val number: Int) {

    fun getIcon(): Int {

        return when (type) {
            "food" -> R.drawable.food
            "health" -> R.drawable.health
            else -> R.drawable.food
        }
    }

    fun getProviderType(): String {
        return when (type) {
            "food" -> "مزودين الغذاء"
            "health" -> "إسعافات أولية"
            else -> ""
        }

    }


    fun getProviderNumber(): String {
        return " عدد$number متطوع "
    }
}