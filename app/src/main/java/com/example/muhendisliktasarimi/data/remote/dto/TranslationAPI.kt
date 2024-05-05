package com.example.muhendisliktasarimi.data.remote.dto

import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.domain.model.TranslationResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TranslationAPI {
    @POST("translator/translate")
    @Headers("Authorization: apikey 6tQwd26S0qCYc6FheerKIT:4nmQNp4Jx9WCauA0L6UAUL", "Content-Type: application/json")
    suspend fun translateText(
        @Body request: TranslationRequest
    ): TranslationResponse
}