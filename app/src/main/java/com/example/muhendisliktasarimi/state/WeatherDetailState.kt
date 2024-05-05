package com.example.muhendisliktasarimi.state

import com.example.muhendisliktasarimi.data.remote.dto.weather.WeatherDto

data class WeatherDetailState(
    val isLoading: Boolean = false,
    val weather: WeatherDto? = null,
    val error: String = "",
)