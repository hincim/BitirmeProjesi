package com.example.muhendisliktasarimi.data.remote.dto


import com.example.muhendisliktasarimi.data.remote.dto.movie.MovieDetailDto
import com.example.muhendisliktasarimi.data.remote.dto.movie.MoviesDto
import com.example.muhendisliktasarimi.util.Constants.MOVIE_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString: String,
        @Query("apikey") apiKey: String = MOVIE_API_KEY
    ): MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = MOVIE_API_KEY
    ): MovieDetailDto
}











