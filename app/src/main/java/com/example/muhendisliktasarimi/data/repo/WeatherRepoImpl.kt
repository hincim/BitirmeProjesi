package com.example.muhendisliktasarimi.data.repo


import com.example.muhendisliktasarimi.data.remote.dto.WeatherAPI
import com.example.muhendisliktasarimi.domain.repo.WeatherRepo
import com.example.muhendisliktasarimi.data.remote.dto.weather.WeatherDto
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val api: WeatherAPI
) : WeatherRepo {

    override suspend fun getWeather(city: String): WeatherDto {
        return api.getWeather(city)
    }
}