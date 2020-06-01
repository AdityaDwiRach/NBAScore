package com.adr.nbascore.adapter

import com.adr.nbascore.model.last_game.LastGame

interface ISearchAdapterModel {
    fun setListData(listData: List<LastGame>)
    fun setSearchTeam(teamName: String)
    fun getSizeData(): Int
    fun getListData(): List<LastGame>
    fun getSearchTeam(): String
}

interface ISearchAdapterView {
    fun refreshData()
}