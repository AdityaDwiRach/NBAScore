package com.adr.nbascore.views

import com.adr.nbascore.model.last_game.LastGame
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL

interface IListTeamFragmentView {
    fun onError(message: String)
    fun showLoading()
    fun hideLoading()
    fun onSuccessListTeam(dataList: List<Team>)
}