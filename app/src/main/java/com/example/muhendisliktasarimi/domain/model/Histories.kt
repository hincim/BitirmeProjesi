package com.example.muhendisliktasarimi.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Histories")
data class Histories(
    @ColumnInfo("past")
    @SerializedName("past")
    val pastName: String?,
    @ColumnInfo("date")
    @SerializedName("date")
    val date: Long = System.currentTimeMillis(),

    @PrimaryKey(true)
    var uuid: Int = 0
)