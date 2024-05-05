package com.example.muhendisliktasarimi.data.repo

import android.view.translation.TranslationResponse
import com.example.muhendisliktasarimi.data.remote.dto.TdkAPI
import com.example.muhendisliktasarimi.data.remote.dto.TranslationAPI
import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.domain.repo.TranslateRepo
import javax.inject.Inject

class TranslateRepoImpl @Inject constructor(
    private val api: TranslationAPI
) : TranslateRepo {
    override suspend fun getText(search: TranslationRequest): com.example.muhendisliktasarimi.domain.model.TranslationResponse {
        return api.translateText(search)
    }
}