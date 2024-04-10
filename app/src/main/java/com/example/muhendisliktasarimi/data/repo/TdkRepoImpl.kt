package com.example.muhendisliktasarimi.data.repo

import com.example.muhendisliktasarimi.data.remote.dto.TdkAPI
import com.example.muhendisliktasarimi.data.remote.dto.tdk.TdkDto
import com.example.muhendisliktasarimi.domain.repo.TdkRepo
import javax.inject.Inject

class TdkRepoImpl @Inject constructor(
    private val api: TdkAPI
): TdkRepo {
    override suspend fun getMean(searchQuery: String): TdkDto {
        return api.getWordMean(searchQuery)
    }
}