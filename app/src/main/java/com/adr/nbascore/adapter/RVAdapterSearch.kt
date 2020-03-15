package com.adr.nbascore.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Color.GREEN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.fragment.SearchTeamFragment
import com.adr.nbascore.model.last_game.LastGame
import kotlinx.android.synthetic.main.list_last_5_games.view.*

class RVAdapterSearch(private var context: Context?, private var dataList: List<LastGame>?, val teamName: String): RecyclerView.Adapter<RVAdapterSearch.ViewHolder>() {
    //TODO straighten the margin data

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateSearch: TextView = itemView.match_date_search
        val timeSearch: TextView = itemView.match_time_search
        val homeScore: TextView = itemView.home_team_score_last5
        val awayScore: TextView = itemView.away_team_score_last5
        val homeTeam: TextView = itemView.home_team_name_last5
        val awayTeam: TextView = itemView.away_team_name_last5
        val resultGameSearch: TextView = itemView.result_game_search
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_last_5_games, parent, false))
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val homeTeam = dataList?.get(position)?.strHomeTeam
        val awayTeam = dataList?.get(position)?.strAwayTeam
        val homeScore = dataList?.get(position)?.intHomeScore
        val awayScore = dataList?.get(position)?.intAwayScore

        holder.dateSearch.text = dataList?.get(position)?.dateEventLocal
        holder.timeSearch.text = dataList?.get(position)?.strTimeLocal

        holder.homeScore.text = homeScore
        holder.homeTeam.text = homeTeam

        holder.awayScore.text = awayScore
        holder.awayTeam.text = awayTeam

        if (awayScore == null || homeScore == null){
            holder.resultGameSearch.visibility = View.INVISIBLE
        } else {
            if (isTeamWin(homeTeam.toString(), awayTeam.toString(), homeScore.toInt(), awayScore.toInt())
            ) {
                holder.resultGameSearch.text = context?.resources?.getString(R.string.win)
                holder.resultGameSearch.setTextColor(context?.resources?.getColor(R.color.dark_green)!!)
            } else {
                holder.resultGameSearch.text = context?.resources?.getString(R.string.lose)
                holder.resultGameSearch.setTextColor(context?.resources?.getColor(R.color.dark_red)!!)
            }
        }
    }

    private fun isTeamWin(homeTeam: String, awayTeam: String, homeScore: Int, awayScore: Int): Boolean{
        if (teamName == homeTeam){
            return homeScore > awayScore
        } else if (teamName == awayTeam){
            return awayScore > homeScore
        }
        return false
    }
}