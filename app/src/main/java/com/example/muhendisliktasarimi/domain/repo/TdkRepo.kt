package com.example.muhendisliktasarimi.domain.repo

import com.example.muhendisliktasarimi.data.remote.dto.tdk.TdkDto


interface TdkRepo {

    suspend fun getMean(searchQuery: String): TdkDto

}