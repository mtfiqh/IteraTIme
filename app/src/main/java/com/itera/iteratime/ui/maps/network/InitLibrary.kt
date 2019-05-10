package com.itera.iteratime.ui.maps.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InitLibrary {
    //https://maps.googleapis.com/maps/api/directions/json?origin=Cirebon,ID&destination=Jakarta,ID&api_key=YOUR_API_KEY
    var BASE_URL = "https://maps.googleapis.com/maps/api/directions/"

    val instance: ApiServices
        get() = setInit().create(ApiServices::class.java)

    fun setInit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
