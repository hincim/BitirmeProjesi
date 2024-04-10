package com.example.muhendisliktasarimi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.muhendisliktasarimi.data.room.AppDatabase
import com.example.muhendisliktasarimi.domain.model.Histories
import kotlinx.coroutines.launch

class HistoriesViewModel(application: Application): BaseViewModel(application) {

    val histories = MutableLiveData<List<Histories>>()

    private fun showHistories(historiesList: List<Histories>){
        histories.value = historiesList
    }

    fun getDataFromSQLite(){
        launch {
            val histories = AppDatabase(getApplication()).historiesDao().getAllHistories()
            showHistories(histories)
        }
    }
    fun saveInSQLite(histories: Histories){
        launch {
            val dao = AppDatabase(getApplication()).historiesDao()
            dao.insert(histories)
        }
    }
    fun deleteHistory(history: Histories){
        launch {
            val dao = AppDatabase(getApplication()).historiesDao()
            dao.deleteHistory(history)
            getDataFromSQLite()
        }
    }

    fun deleteAllHistory(){
        launch {
            val dao = AppDatabase(getApplication()).historiesDao()
            dao.deleteAllHistory()
            getDataFromSQLite()
        }
    }
}














