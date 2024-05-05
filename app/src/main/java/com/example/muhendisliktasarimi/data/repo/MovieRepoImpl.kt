package com.example.muhendisliktasarimi.data.repo


import com.example.muhendisliktasarimi.data.remote.dto.MovieAPI
import com.example.muhendisliktasarimi.data.remote.dto.movie.MovieDetailDto
import com.example.muhendisliktasarimi.data.remote.dto.movie.MoviesDto
import com.example.muhendisliktasarimi.domain.repo.MovieRepo
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val api: MovieAPI
): MovieRepo {
    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(search)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId)
    }
}