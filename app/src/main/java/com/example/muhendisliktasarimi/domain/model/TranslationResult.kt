package com.example.muhendisliktasarimi.domain.model

import com.google.gson.annotations.SerializedName

data class TranslationResult(
    @SerializedName("text") val translatedText: String
)