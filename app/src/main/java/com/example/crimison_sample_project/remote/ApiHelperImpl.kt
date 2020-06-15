package com.mindorks.example.coroutines.data.api

import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.models.SearchX
import com.example.crimison_sample_project.remote.ApiHelper
import com.example.crimison_sample_project.remote.ApiService
import retrofit2.Response
import retrofit2.http.Query

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun searchData(type:String, apikey: String, page: String, s: String)=apiService.searchData(type,apikey,page,s)
    override suspend fun getDetail(plot:String,apikey: String,t: String)=apiService.getDetail(plot,apikey,t)




}