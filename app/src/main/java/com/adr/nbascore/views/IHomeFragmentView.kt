package com.adr.nbascore.views

import com.adr.nbascore.model.current_match.CurrentMatch

interface IHomeFragmentView{
    fun onError(message: String)
    fun showLoading()
    fun hideLoading()
    fun onSuccessData(listData: List<CurrentMatch>)
    fun onSuccessTeamLogo(listTeamName: List<String>, listTeamLogo: List<String>)
}