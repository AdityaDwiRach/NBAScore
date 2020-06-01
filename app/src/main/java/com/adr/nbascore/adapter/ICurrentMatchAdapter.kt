package com.adr.nbascore.adapter

import com.adr.nbascore.model.current_match.CurrentMatch

interface ICurrentMatchAdapterModel {
    fun setListData(listData: List<CurrentMatch>)
    fun setListTeamName(listData: List<String>)
    fun setListTeamLogo(listData: List<String>)
    fun getSizeData(): Int
    fun getListData(): List<CurrentMatch>
    fun getListTeamName(): List<String>?
    fun getListTeamLogo(): List<String>?
}

interface ICurrentMatchAdapterView {
    fun refreshData()
}