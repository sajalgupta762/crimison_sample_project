package com.example.crimison_sample_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimison_sample_project.models.Search
import id.rizmaulana.covid19.data.repository.AppRepository
import retrofit2.Response
import androidx.lifecycle.liveData
import com.example.crimison_sample_project.remote.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {



    fun getSearchData(type:String,apikey: String,page: String,s: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getSearch(type,apikey,page,s)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }







}