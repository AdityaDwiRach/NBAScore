package com.adr.nbascore.api

import com.adr.nbascore.model.last_game.LastGameL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APILastGameInterface {//TODO implement rxjava

    @GET("eventslast.php")
//            "?id=134880")
    fun getDataLastGame(@Query("id") id:Int?): Call<LastGameL>
//        @Query("id") id:String?): Call<LastGameL>

}