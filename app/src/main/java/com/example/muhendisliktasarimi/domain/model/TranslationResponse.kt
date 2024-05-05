package com.example.muhendisliktasarimi.domain.model

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val result: TranslationResult
)