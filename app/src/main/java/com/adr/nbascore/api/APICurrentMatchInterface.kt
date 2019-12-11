package com.adr.nbascore.api

import com.adr.nbascore.model.current_match.CurrentMatchL
import retrofit2.Call
import retrofit2.http.GET

interface APICurrentMatchInterface {
    @GET("eventspastleague.php?id=4387")
    fun getDataCurrentMatch(): Call<CurrentMatchL>
}