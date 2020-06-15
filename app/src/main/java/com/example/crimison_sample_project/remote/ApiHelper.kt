package com.example.crimison_sample_project.remote

import com.example.crimison_sample_project.models.Details
import com.example.crimison_sample_project.models.Search
import retrofit2.Response
import retrofit2.http.Query

interface ApiHelper {
    //suspend fun getSearchdata(@Query("type") type:String, @Query("apikey") apikey: String, @Query("page") page: String, @Query("s") s: String) = apiService.searchData(type,apikey,"1","s")
  //  suspend fun getDetails(@Query("plot") plot:String, @Query("apikey") apikey: String, @Query("t") t: String) = apiService.searchDetail(plot,apikey,apikey,t)


    suspend fun searchData(type:String,apikey: String, page: String, s: String): Response<Search>
    suspend fun getDetail(plot:String,apikey: String, t: String): Response<Details>







}
