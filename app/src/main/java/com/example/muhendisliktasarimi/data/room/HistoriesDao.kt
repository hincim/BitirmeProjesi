package com.example.muhendisliktasarimi.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.muhendisliktasarimi.domain.model.Histories
@Dao
interface HistoriesDao {
    @Insert
    suspend fun insert(history: Histories)
    @Query("SELECT * FROM Histories ORDER BY date DESC")
    suspend fun getAllHistories() : List<Histories>
    @Delete
    suspend fun deleteHistory(history: Histories)
    @Query("DELETE FROM Histories")
    suspend fun deleteAllHistory()

}