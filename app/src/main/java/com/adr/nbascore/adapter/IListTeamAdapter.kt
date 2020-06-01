package com.adr.nbascore.adapter

import com.adr.nbascore.model.last_game.LastGame
import com.adr.nbascore.model.list_team.Team

interface IListTeamAdapterModel {
    fun setListData(listData: List<Team>)
    fun getSizeData(): Int
    fun getListData(): List<Team>
}

interface IListTeamAdapterView {
    fun refreshData()
}