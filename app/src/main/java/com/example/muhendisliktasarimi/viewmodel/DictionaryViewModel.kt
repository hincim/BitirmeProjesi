package com.example.muhendisliktasarimi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.domain.model.TranslationResponse
import com.example.muhendisliktasarimi.domain.model.TranslationResult
import com.example.muhendisliktasarimi.domain.use_case.GetTranslateUseCase
import com.example.muhendisliktasarimi.event.MoviesEvent
import com.example.muhendisliktasarimi.event.TranslateEvent
import com.example.muhendisliktasarimi.state.MoviesState
import com.example.muhendisliktasarimi.state.TranslateState
import com.example.muhendisliktasarimi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val getTranslateUseCase: GetTranslateUseCase
) : ViewModel(){

    private val _state = MutableLiveData<TranslateState>()
    val state: LiveData<TranslateState>
        get() = _state

    private var job: Job? = null


    init {
/*
        getTranslate("harry")
*/
    }

    private fun getTranslate(search:TranslationRequest){
        job?.cancel()
        job = getTranslateUseCase.executeGetTranslate(search).onEach {


            /*when(it){
                is Resource.Success ->{
                    _state.value = TranslateState(mean = it.data ?: TranslationResponse(false,
                        TranslationResult("")
                    ))
                    println("Success")
                }
                is Resource.Loading ->{
                    _state.value = _state.value?.copy(isLoading = true)
                }
                is Resource.Error ->{
                    _state.value = TranslateState(error = "Error")
                }
            }*/
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: TranslateEvent){
        when(event){
            is TranslateEvent.Search ->{
                getTranslate(event.searchString)

            }
        }
    }

}