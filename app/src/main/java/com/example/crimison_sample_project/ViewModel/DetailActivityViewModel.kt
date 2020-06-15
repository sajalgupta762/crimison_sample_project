package com.example.crimison_sample_project.ViewModel

import androidx.lifecycle.*
import com.example.crimison_sample_project.DataBase.DatabaseHelper
import com.example.crimison_sample_project.models.Details
import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.remote.ApiHelper
import com.example.crimison_sample_project.Utils.Resource
import id.rizmaulana.covid19.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailActivityViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
    ) : ViewModel() {

    private val users = MutableLiveData<Resource<Response<Details>>>()

        init {
            fetchUsers()
        }

        private fun fetchUsers() {
            viewModelScope.launch {
                users.postValue(Resource.loading(null))
                try {
                    val usersFromApi = apiHelper.getDetail("full","5d81e1ce","guardians")
                    users.postValue(Resource.success(usersFromApi))
                } catch (e: Exception) {
                    users.postValue(Resource.error(e.toString(), null))
                }
            }
        }

        fun getDetail(): LiveData<Resource<Response<Details>>> {
            return users
        }
}