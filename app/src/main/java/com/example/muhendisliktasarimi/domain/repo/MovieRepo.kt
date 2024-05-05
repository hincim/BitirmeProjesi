package com.example.muhendisliktasarimi.domain.repo

import com.example.muhendisliktasarimi.data.remote.dto.movie.MovieDetailDto
import com.example.muhendisliktasarimi.data.remote.dto.movie.MoviesDto


interface MovieRepo {

    suspend fun getMovies(search: String) : MoviesDto
    suspend fun getMovieDetail(imdbId: String) : MovieDetailDto
}