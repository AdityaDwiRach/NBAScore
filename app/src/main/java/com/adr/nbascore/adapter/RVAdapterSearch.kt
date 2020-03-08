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

class RVAdapterSearch(private var context: Context?, private var dataList: List<LastGame>?): RecyclerView.Adapter<RVAdapterSearch.ViewHolder>() {
    //TODO straighten the margin data

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateSearch = itemView.match_date_search
        val timeSearch = itemView.match_time_search
        val homeScore = itemView.home_team_score_last5
        val awayScore = itemView.away_team_score_last5
        val homeTeam = itemView.home_team_name_last5
        val awayTeam = itemView.home_team_name_last5
        val resultGameSearch = itemView.result_game_search
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_last_5_games, parent, false))
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateSearch.text = dataList?.get(position)?.dateEventLocal
        holder.timeSearch.text = dataList?.get(position)?.strTimeLocal

        holder.homeScore.text = dataList?.get(position)?.intHomeScore
        holder.homeTeam.text = dataList?.get(position)?.strHomeTeam

        holder.awayScore.text = dataList?.get(position)?.intAwayScore
        holder.awayTeam.text = dataList?.get(position)?.strAwayTeam
    }
}