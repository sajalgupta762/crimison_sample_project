package com.example.crimison_sample_project.DataBase

import androidx.room.Insert
import androidx.room.OnConflictStrategy

class DatabaseHelperImpl(private val appDatabase: AppDatabase): DatabaseHelper{

    override suspend fun insert(record:SearchXDB)=appDatabase.Dao().insertPosterData(record)
    override suspend fun insertAll(records:List<SearchXDB>)=appDatabase.Dao().insertAll(records)


}