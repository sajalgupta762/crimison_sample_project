package com.example.crimison_sample_project.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataAccessObject {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(records: List<SearchXDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosterData(record :SearchXDB)
}