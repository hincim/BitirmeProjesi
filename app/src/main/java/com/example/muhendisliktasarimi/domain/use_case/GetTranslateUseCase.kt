package com.example.muhendisliktasarimi.domain.use_case

import com.example.muhendisliktasarimi.domain.model.TranslationRequest
import com.example.muhendisliktasarimi.domain.model.TranslationResponse
import com.example.muhendisliktasarimi.domain.repo.TranslateRepo
import com.example.muhendisliktasarimi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.lang.Exception
import javax.inject.Inject

class GetTranslateUseCase@Inject constructor(
    private val repo: TranslateRepo
) {

    fun executeGetTranslate(search: TranslationRequest): Flow<TranslationResponse> = flow {

        val text = repo.getText(search)
        println("text")

        println(text)
        emit(text)
    }
}