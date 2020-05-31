package com.adr.nbascore.model.api

import com.adr.nbascore.model.current_match.CurrentMatchL
import io.reactivex.Observable
import retrofit2.http.GET

interface APICurrentMatchInterface {
    @GET("eventspastleague.php?id=4387")
    fun getDataCurrentMatch(): Observable<CurrentMatchL>
}