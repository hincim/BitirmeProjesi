package com.example.muhendisliktasarimi.domain.use_case


import com.example.muhendisliktasarimi.data.remote.dto.weather.toWeatherList
import com.example.muhendisliktasarimi.domain.model.WeatherModel
import com.example.muhendisliktasarimi.domain.repo.WeatherRepo
import com.example.muhendisliktasarimi.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repo: WeatherRepo
) {

    fun executeGetWeather(city: String): kotlinx.coroutines.flow.Flow<Resource<List<WeatherModel>>> = flow{

        try {
            emit(Resource.Loading())
            val weather = repo.getWeather(city)
            emit(Resource.Success(weather.toWeatherList()))
        }catch (e: IOError){
            emit(Resource.Error(message = "No internet connection"))
        }catch (e: HttpException){
            emit(Resource.Error(message = "Error"))
        }

    }

}