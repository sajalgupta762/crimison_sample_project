package com.example.crimison_sample_project.remote

import com.example.crimison_sample_project.models.Details
import com.example.crimison_sample_project.models.Search
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun searchData(@Query("type") type:String, @Query("apikey") apikey: String, @Query("page") page: String, @Query("s") s: String): Response<Search>

    @GET("/")
    suspend fun getDetail(@Query("plot") type:String, @Query("apikey") apikey: String,@Query("t") t: String): Response<Details>

}