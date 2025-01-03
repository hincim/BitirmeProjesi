package com.example.muhendisliktasarimi.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.muhendisliktasarimi.domain.model.Words

@Dao
interface WordsDao {

    @Insert
    suspend fun insertAll(vararg words: Words): List<Long>

    @Insert
    suspend fun insert(words: Words): Long

    @Query("SELECT * FROM Words WHERE eng = :englishWord LIMIT 1")
    suspend fun getWordByEnglish(englishWord: String): Words?

    @Query("DELETE FROM words") // Tüm kelimeleri silmek için
    suspend fun deleteAllWords()

    @Query("SELECT * FROM Words ORDER BY eng ASC")
    suspend fun getAllWords() : List<Words>

    @Query("SELECT * FROM Words WHERE eng LIKE '%' || :word ||'%' OR tr LIKE '%' || :word ||'%' ORDER BY eng ASC")
    suspend fun getWordBySearch(word: String): List<Words>

    @Delete
    suspend fun deleteWords(words: Words)

    @Query("SELECT * FROM Words ORDER BY RANDOM()")
    suspend fun getRandomWords(): List<Words>

    @Query("SELECT * FROM Words WHERE uuid != :uuid ORDER BY RANDOM() LIMIT 3")
    suspend fun getRandomOptionsWord(uuid: Int): List<Words>

    @Query("UPDATE words SET eng = :engWord, tr = :trWord WHERE uuid = :id")
    suspend fun updateWordById(id: Int, engWord: String, trWord: String): Int

}