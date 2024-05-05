package com.example.muhendisliktasarimi.domain.repo

import com.example.muhendisliktasarimi.data.remote.dto.weather.WeatherDto

interface WeatherRepo {

    suspend fun getWeather(city: String): WeatherDto
}