package com.adr.nbascore.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"
        var retrofit: Retrofit? = null

        val client: Retrofit?
            get() {
            if (retrofit == null){

                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}