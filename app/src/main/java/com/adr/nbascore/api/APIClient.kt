package com.adr.learnjson2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"
        var retrofit: Retrofit? = null

        val client:Retrofit get() {
            if (retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}