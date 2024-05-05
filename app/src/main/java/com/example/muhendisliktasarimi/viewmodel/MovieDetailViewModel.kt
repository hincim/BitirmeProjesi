package com.example.muhendisliktasarimi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhendisliktasarimi.domain.use_case.GetMovieDetailUseCase
import com.example.muhendisliktasarimi.state.MovieDetailState
import com.example.muhendisliktasarimi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
): ViewModel() {

    private val _state = MutableLiveData<MovieDetailState>()
    val state: LiveData<MovieDetailState>
        get() = _state

    private var job: Job? = null


    fun getMovieDetail(imdbId: String){
        job?.cancel()
        job = getMovieDetailUseCase.executeGetMovieDetails(imdbId).onEach {

            when(it){
                is Resource.Success ->{
                    _state.value = MovieDetailState(movies = it.data)
                }
                is Resource.Loading ->{
                    _state.value = _state.value?.copy(isLoading = true)
                }
                is Resource.Error ->{
                    _state.value = MovieDetailState(error = "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}