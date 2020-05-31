package com.adr.nbascore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.current_match.CurrentMatch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_current_match.view.*

class RVAdapterCurrentMatch : RecyclerView.Adapter<RVAdapterCurrentMatch.ViewHolder>(), ICurrentMatchAdapterModel, ICurrentMatchAdapterView {

    private var dataList: List<CurrentMatch> = ArrayList<CurrentMatch>()
    private var dataListName: List<String>? = null
    private var dataListLogo: List<String>? = null
    private var context: Context? = null


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val matchDate = itemView.match_date!!
        val matchTime = itemView.match_time!!

        val homeTeamName = itemView.home_team_name!!
        val awayTeamName = itemView.away_team_name!!

        val homeTeamScore = itemView.home_team_score!!
        val awayTeamScore = itemView.away_team_score!!

        val homeTeamLogo = itemView.home_team_logo!!
        val awayTeamLogo = itemView.away_team_logo!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_current_match, parent, false))
    }

    override fun getItemCount(): Int = getSizeData()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataListBind = getListData()

        holder.matchDate.text = dataListBind[position].dateEventLocal
        holder.matchTime.text = dataListBind[position].strTimeLocal

        holder.homeTeamScore.text = dataListBind[position].intHomeScore
        holder.homeTeamName.text = dataListBind[position].strHomeTeam

        holder.awayTeamScore.text = dataListBind[position].intAwayScore
        holder.awayTeamName.text = dataListBind[position].strAwayTeam

        val imageHomeTeamLogo = holder.homeTeamLogo
        val imageAwayTeamLogo = holder.awayTeamLogo

        val dataListNameBind = getListTeamName()
        val dataListLogoBind = getListTeamLogo()
        if (dataListNameBind != null && dataListLogoBind != null) {
            val idHome = dataListNameBind.indexOf(dataListBind[position].strHomeTeam)
            val idAway = dataListNameBind.indexOf(dataListBind[position].strAwayTeam)

            Picasso.get().load(dataListLogoBind[idHome]).fit().into(imageHomeTeamLogo)
            Picasso.get().load(dataListLogoBind[idAway]).fit().into(imageAwayTeamLogo)
        }
    }

    override fun setListData(listData: List<CurrentMatch>) {
        this.dataList = listData
    }

    override fun setListTeamName(listData: List<String>) {
        this.dataListName = listData
    }

    override fun setListTeamLogo(listData: List<String>) {
        this.dataListLogo = listData
    }

    override fun refreshData() {
        notifyDataSetChanged()
    }

    override fun getSizeData(): Int {
        return dataList.size
    }

    override fun getListData(): List<CurrentMatch> {
        return dataList
    }

    override fun getListTeamLogo(): List<String>? {
        return if (dataListLogo != null){
            dataListLogo!!
        } else {
            null
        }
    }

    override fun getListTeamName(): List<String>? {
        return if (dataListName != null){
            dataListName!!
        } else {
            null
        }
    }
}