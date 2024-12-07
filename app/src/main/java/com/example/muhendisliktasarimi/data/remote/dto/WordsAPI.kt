package com.example.muhendisliktasarimi.data.remote.dto

import DictionaryResponse
import com.example.muhendisliktasarimi.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsAPI {

    @GET("lookup")
    fun getTranslation(
        @Query("lang") lang: String,
        @Query("text") text: String,
        @Query("key") apiKey: String = Constants.WORD_API_KEY
    ): Call<DictionaryResponse>
}