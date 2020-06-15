package com.example.crimison_sample_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimison_sample_project.DataBase.DatabaseHelper
import com.example.crimison_sample_project.DataBase.SearchXDB
import com.example.crimison_sample_project.Utils.Resource
import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.remote.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {
    private val users = MutableLiveData<Resource<Response<Search>>>()
    private lateinit var usersFromApi :Response<Search>
    private  var usersToInsertInDB = mutableListOf<SearchXDB>()
    init {
       // fetchUsers()
    }

    public fun fetchUsers(type:String,apikey: String,page: String,s: String) {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
          //  val usersToInsertInDB = mutableListOf<SearchXDB>()
            try {
                usersFromApi = apiHelper.searchData(type,apikey,page,s)

                for (apiUser in usersFromApi.body()!!.Search) {
                    val searchXDB = SearchXDB(
                        apiUser.imdbID,
                        apiUser.Type,
                        apiUser.Year,
                        "1",
                        apiUser.Poster
                    )
                    usersToInsertInDB.add(searchXDB)
                }
                 Thread.currentThread().name

                // To Add Latest Server data to database
                val t = Thread(Runnable {
                    launch(Dispatchers.IO){
                        dbHelper.insertAll(usersToInsertInDB)
                    }
                })
               t.start()
                users.postValue(Resource.success(usersFromApi))

            } catch (e: Exception) {
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getSearch(): LiveData<Resource<Response<Search>>> {
        return users
    }

    fun searchdata(type:String,apikey: String,page: String,s: String) {
        fetchUsers(type,apikey,page,s)

    }

   suspend fun  insertFreshDataToLocalCache() {
       //
    }

}