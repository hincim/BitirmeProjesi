package com.example.muhendisliktasarimi.domain.model

import com.google.gson.annotations.SerializedName

data class TranslationRequest(
    @SerializedName("from") val fromLanguage: String,
    @SerializedName("to") val toLanguage: String,
    @SerializedName("text") val text: String
)