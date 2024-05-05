package com.example.muhendisliktasarimi.state

import com.example.muhendisliktasarimi.data.remote.dto.tdk.TdkDto
import com.example.muhendisliktasarimi.domain.model.TranslationResponse

data class TranslateState (
    val isLoading: Boolean = false,
    val mean: TranslationResponse? = null,
    val error: String = ""
    )