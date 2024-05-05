package com.example.muhendisliktasarimi.data.remote.dto.movie


import com.example.muhendisliktasarimi.domain.model.Movie


data class MoviesDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)

fun MoviesDto.toMovieList() :List<Movie> {
    return Search.map { search -> Movie(search.Poster,search.Title,search.Year,search.imdbID) }
}