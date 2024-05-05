package com.example.muhendisliktasarimi.data.remote.dto

import com.example.muhendisliktasarimi.data.remote.dto.weather.WeatherDto
import com.example.muhendisliktasarimi.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric")
    : WeatherDto
}