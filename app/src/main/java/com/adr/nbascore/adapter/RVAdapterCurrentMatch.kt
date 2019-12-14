package com.adr.nbascore.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.current_match.CurrentMatch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_current_match.view.*

class RVAdapterCurrentMatch(
    var context: Context, var dataList: List<CurrentMatch>, var dataListTeamName: ArrayList<String>?, var dataListTeamLogo: ArrayList<String>?
): RecyclerView.Adapter<RVAdapterCurrentMatch.ViewHolder>() {

//    var dataListName = HomeFragment()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val matchDate = itemView.match_date
        val matchTime = itemView.match_time

        val homeTeamName = itemView.home_team_name
        val awayTeamName = itemView.away_team_name

        val homeTeamScore = itemView.home_team_score
        val awayTeamScore = itemView.away_team_score

        val homeTeamLogo = itemView.home_team_logo
        val awayTeamLogo = itemView.away_team_logo
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

        val idHome = dataListTeamName?.indexOf(dataList[position].strHomeTeam)
        val idAway = dataListTeamName?.indexOf(dataList[position].strAwayTeam)

        val imageHomeTeamLogo = holder.homeTeamLogo
        val imageAwayTeamLogo = holder.awayTeamLogo

        Picasso.get().load(dataListTeamLogo?.get(idHome!!)).fit().into(imageHomeTeamLogo)
//        Picasso.get().load(dataList[position].strTeamBadge).fit().into(imageTeamLogo)
        Picasso.get().load(dataListTeamLogo?.get(idAway!!)).fit().into(imageAwayTeamLogo)

        holder.itemView.setOnClickListener {
            Log.i("testiiiiing", "Home : $idHome; Away : $idAway" )
        }
    }
}