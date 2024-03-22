package com.example.muhendisliktasarimi.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.muhendisliktasarimi.domain.model.Words

@Dao
interface WordsDao {

    @Insert
    suspend fun insertAll(vararg words: Words): List<Long>

    @Query("SELECT * FROM Words ORDER BY eng ASC")
    suspend fun getAllWords() : List<Words>

    @Query("SELECT * FROM Words WHERE eng LIKE '%' || :word ||'%' OR tr LIKE '%' || :word ||'%' ORDER BY eng ASC")
    suspend fun getWordBySearch(word: String): List<Words>

    @Delete
    suspend fun deleteWords(words: Words)
}