package com.adr.nbascore.api

import com.adr.nbascore.model.last_game.LastGameL
import com.adr.nbascore.model.test.test
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APILastGameInterface {//TODO the API didn't work. Still need to wait for the forum response

    @GET("eventspastleague.php?id=4328")
    fun getDataLastGame(): Call<LastGameL>
//        @Query("id") id:String?): Call<LastGameL>

}