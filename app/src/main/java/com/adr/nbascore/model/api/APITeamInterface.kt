package com.adr.nbascore.model.api

import com.adr.nbascore.model.list_team.TeamL
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APITeamInterface {
    @GET("search_all_teams.php?l=NBA")
    fun getDataAllTeam(): Observable<TeamL>

    @GET("searchteams.php")
    fun getDataTeam(@Query("t") t:String?): Call<TeamL>
}