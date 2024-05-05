package com.example.muhendisliktasarimi.state

import com.example.muhendisliktasarimi.domain.model.MovieDetail


data class MovieDetailState(
    val isLoading: Boolean = false,
    val movies: MovieDetail? = null,
    val error: String = "",
)