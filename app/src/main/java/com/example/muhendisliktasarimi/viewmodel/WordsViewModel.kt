package com.example.muhendisliktasarimi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.muhendisliktasarimi.data.room.AppDatabase
import com.example.muhendisliktasarimi.domain.model.Words
import kotlinx.coroutines.launch

class WordsViewModel(application: Application): BaseViewModel(application) {

    val words = MutableLiveData<List<Words>>()

    private fun showWords(wordsList: List<Words>){
        words.value = wordsList
    }

    fun getDataFromSQLite(){

        launch {
            val words = AppDatabase(getApplication()).wordsDao().getAllWords()
            showWords(words)
        }
    }

    fun saveInSQLite(words: List<Words>){
        launch {
            val dao = AppDatabase(getApplication()).wordsDao()
            val wordsLong = dao.insertAll(*words.toTypedArray())
            var i = 0
            while (i<words.size){
                words[i].uuid = wordsLong[i].toInt()
                i += 1
            }
            showWords(words)
        }

    }

    fun getWordBySearch(word: String){
        launch {
            val words = AppDatabase(getApplication()).wordsDao().getWordBySearch(word)
            showWords(words)
        }
    }

    fun deleteWords(words: Words){
       launch {
           val dao = AppDatabase(getApplication()).wordsDao()
           dao.deleteWords(words)
           getDataFromSQLite()
       }
    }
}