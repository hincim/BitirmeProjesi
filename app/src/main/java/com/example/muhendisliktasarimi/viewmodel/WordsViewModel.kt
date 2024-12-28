package com.example.muhendisliktasarimi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muhendisliktasarimi.data.room.AppDatabase
import com.example.muhendisliktasarimi.domain.model.Words
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WordsViewModel(application: Application): BaseViewModel(application) {

    val words = MutableLiveData<List<Words>>()
    private val _randomWords = MutableLiveData<List<Words>>()
    private val _randomOptionsWords = MutableLiveData<List<Words>>()

    val randomWords: LiveData<List<Words>> = _randomWords
    val randomOptionsWords: LiveData<List<Words>> = _randomOptionsWords
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

    fun saveInSQLiteWord(words: Words){
        launch {
            val dao = AppDatabase(getApplication()).wordsDao()
            if (!checkIfWordExists(words.engWord!!)){
                dao.insert(words)
                getDataFromSQLite()
            }
        }

    }

    fun clearAllWords() {
       launch {
            AppDatabase(getApplication()).wordsDao().deleteAllWords()
           getDataFromSQLite()
       }
    }
    suspend fun checkIfWordExists(word: String): Boolean {
        return AppDatabase(getApplication()).wordsDao().getWordByEnglish(word) != null
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

    fun updateWordById(id: Int, engWord: String, trWord: String){
        launch {
            val dao = AppDatabase(getApplication()).wordsDao()
            dao.updateWordById(id,engWord,trWord)
            getDataFromSQLite()
        }
    }

    fun getRandomWord(): Deferred<List<Words>> {
        return CoroutineScope(Dispatchers.Default).async {
            val dao = AppDatabase(getApplication()).wordsDao()
            return@async dao.getRandomWords()
        }
    }

    fun getRandomOptionsWord(uuid: Int) :Deferred<List<Words>> {
        return CoroutineScope(Dispatchers.Default).async {
            val dao = AppDatabase(getApplication()).wordsDao()
            return@async dao.getRandomOptionsWord(uuid)
        }
    }
}

