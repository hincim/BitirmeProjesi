package com.example.muhendisliktasarimi.domain.use_case


import com.example.muhendisliktasarimi.data.remote.dto.movie.toMovieList
import com.example.muhendisliktasarimi.domain.model.Movie
import com.example.muhendisliktasarimi.domain.repo.MovieRepo
import com.example.muhendisliktasarimi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.lang.Exception
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repo: MovieRepo
) {

    fun executeGetMovies(search: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movieList = repo.getMovies(search)
            if (movieList.Response.equals("True")){
                emit(Resource.Success(movieList.toMovieList()))
            }else{
                emit(Resource.Error("No movie found"))
            }
        }catch (e: IOError){
            emit(Resource.Error(message = "No internet connection"))
        }catch (e: HttpException){
            emit(Resource.Error(message = "Error"))
        }catch (e:Exception){
            emit(Resource.Error(message = "No internet connection"))
        }
    }
}