package com.adr.nbascore.views

import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.last_game.LastGame

interface ISearchFragmentView {
    fun onError(message: String)
    fun showLoading()
    fun hideLoading()
    fun onSuccessSearchTeam(teamName: String, teamDescription: String, teamBadge: String, teamLogo: String, last5Games: List<LastGame>)
}