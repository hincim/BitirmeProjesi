package com.example.muhendisliktasarimi.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Words")
data class Words(
    @ColumnInfo("eng")
    @SerializedName("eng")
    val engWord: String?,
    @ColumnInfo("tr")
    @SerializedName("tr")
    val trWord: String?,


    @PrimaryKey(true)
    var uuid: Int = 0
)