package com.adr.nbascore.api

import com.adr.nbascore.model.list_team.TeamL
import retrofit2.Call
import retrofit2.http.GET

interface APITeamInterface {
    @GET("search_all_teams.php?l=NBA")
    fun getDataTeam(): Call<TeamL>
}