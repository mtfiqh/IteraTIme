package com.itera.iteratime.ui.maps.network

import com.itera.iteratime.ui.maps.respone.ResponseRoute
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    //https://maps.googleapis.com/maps/api/directions/
    // json?origin=Cirebon,ID&destination=Jakarta,ID&api_key=YOUR_API_KEY
    @GET("json")
    fun request_route(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("api_key") api_key: String
    ): Call<ResponseRoute>
}
