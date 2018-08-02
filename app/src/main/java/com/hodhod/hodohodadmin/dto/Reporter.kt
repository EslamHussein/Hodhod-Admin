package com.hodhod.hodohodadmin.dto

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Eslam Hussein on 8/2/18.
 */
data class Reporter(var name: String = "Reporter", var lat: Double = 0.0, var lon: Double = 0.0, val speciality: String = "") {

    fun getLocation(): LatLng = LatLng(lat, lon)

}