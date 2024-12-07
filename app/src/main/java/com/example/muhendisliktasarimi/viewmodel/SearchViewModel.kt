package com.example.muhendisliktasarimi.viewmodel

import DictionaryResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhendisliktasarimi.data.remote.dto.search_words.Head
import com.example.muhendisliktasarimi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

//@HiltViewModel
//class SearchViewModel @Inject constructor(
//    private val getWordsUseCase: GetWordsUseCase
//): ViewModel() {
//
//    private val _state = MutableLiveData<SearchState>()
//    val state: LiveData<SearchState>
//        get() = _state
//
//    private var job: Job? = null
//
//     fun getWords(search:String){
//        job?.cancel()
//        job = getWordsUseCase.executeGetWords(search).onEach {
//            println("vdfdfdfd")
//            when(it){
//                is Resource.Success ->{
//                    _state.value = it.data?.let { it1 -> SearchState(isLoading = false, words = it1) }
//                }
//                is Resource.Loading ->{
//                    _state.value = _state.value?.copy(isLoading = true)
//                }
//                is Resource.Error ->{
//                    _state.value = SearchState(words = DictionaryResponse(Head(), emptyList()), error = "Error")
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//}