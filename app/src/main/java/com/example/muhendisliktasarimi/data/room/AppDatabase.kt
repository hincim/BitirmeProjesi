package com.example.muhendisliktasarimi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.muhendisliktasarimi.data.WordsDao
import com.example.muhendisliktasarimi.domain.model.Histories
import com.example.muhendisliktasarimi.domain.model.Words


@Database(entities = arrayOf(Words::class, Histories::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun wordsDao() : WordsDao
    abstract fun historiesDao(): HistoriesDao

    companion object{

        @Volatile private var instance: AppDatabase? = null
        // değişkeni farklı threadlere görünür hale getirir.
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){

            instance ?: makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,"app_database").build()

    }
}