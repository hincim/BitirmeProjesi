package com.example.muhendisliktasarimi.state

import com.example.muhendisliktasarimi.data.remote.dto.tdk.TdkDto

data class WordsState(
    val isLoading: Boolean = false,
    val mean: TdkDto? = null,
    val error: String = "",
)