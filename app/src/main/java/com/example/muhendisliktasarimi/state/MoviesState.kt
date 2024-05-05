package com.example.muhendisliktasarimi.state

import com.example.muhendisliktasarimi.domain.model.Movie


data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val search: String = "harry potter"
)