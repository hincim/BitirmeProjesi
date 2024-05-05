package com.example.muhendisliktasarimi.data.remote.dto.weather

import com.example.muhendisliktasarimi.domain.model.WeatherModel
import com.hakanninc.wordhive.data.remote.dto.weather.Clouds
import com.hakanninc.wordhive.data.remote.dto.weather.Coord
import com.hakanninc.wordhive.data.remote.dto.weather.Main
import com.hakanninc.wordhive.data.remote.dto.weather.Sys
import com.hakanninc.wordhive.data.remote.dto.weather.Weather
import com.hakanninc.wordhive.data.remote.dto.weather.Wind

data class WeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherDto.toWeatherList() :List<WeatherModel> {
    return weather.map { weather -> WeatherModel(weather.description,weather.icon) }
}
