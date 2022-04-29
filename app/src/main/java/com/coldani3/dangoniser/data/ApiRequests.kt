package com.coldani3.dangoniser.data

import com.coldani3.dangoniser.data.api.WeatherInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiRequests {

    @GET
    fun getWeatherForCoords(@Url url: String) : Call<WeatherInformation>
}