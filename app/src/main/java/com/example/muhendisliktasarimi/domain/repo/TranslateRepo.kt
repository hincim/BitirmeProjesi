package com.example.muhendisliktasarimi.domain.repo

import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.domain.model.TranslationResponse

interface TranslateRepo {
    suspend fun getText(search: TranslationRequest) : TranslationResponse
}