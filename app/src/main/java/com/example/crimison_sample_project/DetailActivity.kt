package com.example.crimison_sample_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.crimison_sample_project.DataBase.DatabaseBuilder
import com.example.crimison_sample_project.DataBase.DatabaseHelperImpl
import com.example.crimison_sample_project.Utils.Status
import com.example.crimison_sample_project.ViewModel.DetailActivityViewModel
import com.example.crimison_sample_project.ViewModel.MainActivityViewModel
import com.example.crimison_sample_project.ViewModel.ViewModelFactory
import com.example.crimison_sample_project.models.Details
import com.example.crimison_sample_project.remote.ApiHelper
import com.example.crimison_sample_project.remote.RetrofitBuilder
import com.mindorks.example.coroutines.data.api.ApiHelperImpl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_item_layout.view.*
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailActivityViewModel



    private  var movietitle: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        movietitle=intent.getStringExtra("movietitle")
        setupViewModel()
        setupObservers("full","5d81e1ce",movietitle.toString())

    }


    private fun setupObservers(plot:String, apikey: String,t :String) {
        viewModel.getDetail().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                       // progress_Bar.visibility = View.GONE
                       resource.data?.let { response -> render(response) }
                    }
                    Status.ERROR -> {

                       // progress_Bar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        progress_Bar.visibility = View.VISIBLE

                    }
                }
            }
        })
    }



    private fun render(response: Response<Details>) {

        ReleasedValue.setText(response.body()?.Released)
        RatedValue.setText(response.body()?.Rated)
        GenreValue.setText(response.body()?.Genre)
        titlevalue.setText(response.body()?.Title)
        yearValue.setText(response.body()?.Year)
        LanguageValue.setText(response.body()?.Language)


        Glide.with(this)
            .load(response.body()?.Poster)
            .into(imageViewPosterDetail)


    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(DetailActivityViewModel::class.java)
    }

}
