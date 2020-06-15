package com.example.crimison_sample_project.remote

import com.example.crimison_sample_project.Utils.ConstantsApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {



    private fun getRetrofit(): Retrofit {



        return Retrofit.Builder()
            .baseUrl(ConstantsApp.URL)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
             .build() //Doesn't require the adapter
    }



    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.addInterceptor(interceptor)
        b.build().connectTimeoutMillis==7000
        b.build().readTimeoutMillis==7000
         return b.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }




    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}