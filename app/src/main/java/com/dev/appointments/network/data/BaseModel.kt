package com.dev.appointments.network.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dev.appointments.network.NetworkApi
import com.dev.appointments.utils.BASE_URL
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseModel: AppCompatActivity() {

    protected val mNetworkApi: NetworkApi

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mNetworkApi = retrofit.create(NetworkApi::class.java)

    }
    fun initDatabase(context : Context){
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
    }

}