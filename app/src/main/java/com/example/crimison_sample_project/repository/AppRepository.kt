package id.rizmaulana.covid19.data.repository

import com.example.crimison_sample_project.models.Details
import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.remote.ApiHelper
import com.example.crimison_sample_project.remote.RetrofitBuilder
import retrofit2.Response
import retrofit2.http.Query

class AppRepository(private val apiHelper: ApiHelper)  {

    suspend fun getSearch(type:String,apikey: String,page: String,s: String): Response<Search> = apiHelper.searchData(type,apikey,page,s)


    //suspend fun getDetails(@Query("plot") plot:String, @Query("apikey") apikey: String, @Query("t") t: String) = apiHelper.getDetails(plot,apikey,t)



}

