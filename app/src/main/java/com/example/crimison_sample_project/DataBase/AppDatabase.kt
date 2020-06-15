package com.example.crimison_sample_project.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchXDB::class],version = 1)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun Dao(): DataAccessObject

}