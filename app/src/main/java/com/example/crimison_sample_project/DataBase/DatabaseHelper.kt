package com.example.crimison_sample_project.DataBase

interface DatabaseHelper {

    suspend fun insert(record:SearchXDB)
    suspend fun insertAll(records:List<SearchXDB>)
}