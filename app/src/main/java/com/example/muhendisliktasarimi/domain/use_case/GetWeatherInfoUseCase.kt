package com.example.muhendisliktasarimi.domain.use_case

import com.example.muhendisliktasarimi.data.remote.dto.weather.WeatherDto
import com.example.muhendisliktasarimi.domain.repo.WeatherRepo
import com.example.muhendisliktasarimi.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(
    private val repo: WeatherRepo
) {

    fun executeWeatherDetails(city: String) : kotlinx.coroutines.flow.Flow<Resource<WeatherDto>> = flow {
        try {
            emit(Resource.Loading())
            val weatherDetail = repo.getWeather(city)
            if (weatherDetail.id != 0){
                emit(Resource.Success(weatherDetail))
            }else{
                emit(Resource.Error("No data"))
            }
        }catch (e: IOError){
            emit(Resource.Error(message = "Error"))
        }catch (e: HttpException){
            emit(Resource.Error(message = "Error"))
        }
        catch (e: Exception){
            emit(Resource.Error(message = "No internet connection"))
        }
    }
}