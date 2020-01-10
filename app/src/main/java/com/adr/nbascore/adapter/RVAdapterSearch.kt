package com.adr.nbascore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.last_game.LastGame
import kotlinx.android.synthetic.main.list_current_match.view.*
import kotlinx.android.synthetic.main.list_last_5_games.view.*

class RVAdapterSearch(var context: Context?, var dataList: List<LastGame>): RecyclerView.Adapter<RVAdapterSearch.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateSearch = itemView.match_date
        val timeSearch = itemView.match_time
        val homeScore = itemView.home_team_score
        val awayScore = itemView.away_team_score
        val homeTeam = itemView.home_team_name
        val awayTeam = itemView.away_team_name
        val resultGameSearch = itemView.result_game_search
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_current_match, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.dateSearch.text = dataList[position].
//        holder.timeSearch.text = dataList[position].strTimeLocal
//
//        holder.homeScore.text = dataList[position].intHomeScore
//        holder.homeTeam.text = dataList[position].strHomeTeam
//
//        holder.awayScore.text = dataList[position].intAwayScore
//        holder.awayTeam.text = dataList[position].strAwayTeam
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, parent, false))
//    }
//
//    override fun getItemCount(): Int = dataList.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val imageTeamLogo = holder.teamLogo
//
//        Picasso.get().load(dataList[position].strTeamBadge).fit().into(imageTeamLogo)
//
//        holder.teamName.text = dataList[position].strTeam
//        holder.stadiumName.text = dataList[position].strStadium
//        holder.stadiumLocation.text = dataList[position].strStadiumDescription
//    }
//
//    override fun onBindViewHolder(holder: RVAdapterListTeam.ViewHolder, position: Int) {
//
//    }
}