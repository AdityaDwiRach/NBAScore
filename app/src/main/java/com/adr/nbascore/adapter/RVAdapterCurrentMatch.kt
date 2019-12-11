package com.adr.nbascore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.learnjson2.adapter.RVAdapterListTeam
import com.adr.nbascore.R
import com.adr.nbascore.model.current_match.CurrentMatch
import kotlinx.android.synthetic.main.list_current_match.view.*
import kotlinx.android.synthetic.main.list_team.view.*

class RVAdapterCurrentMatch(var context: Context, var dataList: List<CurrentMatch>): RecyclerView.Adapter<RVAdapterCurrentMatch.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val matchDate = itemView.match_date
        val matchTime = itemView.match_time

        val homeTeamName = itemView.home_team_name
        val awayTeamName = itemView.away_team_name

        val homeTeamScore = itemView.home_team_score
        val awayTeamScore = itemView.away_team_score
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_current_match, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.matchDate.text = dataList[position].dateEventLocal
        holder.matchTime.text = dataList[position].strTimeLocal

        holder.homeTeamScore.text = dataList[position].intHomeScore
        holder.homeTeamName.text = dataList[position].strHomeTeam

        holder.awayTeamScore.text = dataList[position].intAwayScore
        holder.awayTeamName.text = dataList[position].strAwayTeam
    }
}