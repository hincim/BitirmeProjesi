package com.example.muhendisliktasarimi.event

sealed class MoviesEvent {

    data class Search(val searchString: String): MoviesEvent()
}