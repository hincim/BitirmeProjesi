package com.example.muhendisliktasarimi.event

import com.example.muhendisliktasarimi.domain.model.TranslationRequest

sealed class TranslateEvent{

    data class Search(val searchString: TranslationRequest): TranslateEvent()

}
