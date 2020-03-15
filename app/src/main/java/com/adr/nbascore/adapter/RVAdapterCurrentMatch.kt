package com.adr.nbascore.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.current_match.CurrentMatch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_current_match.view.*
import java.lang.reflect.Type


class RVAdapterCurrentMatch(
    private var context: Context?, private var dataList: List<CurrentMatch>): RecyclerView.Adapter<RVAdapterCurrentMatch.ViewHolder>() {

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

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "homescore : ${dataList[position].intHomeScore}, awayscore : ${dataList[position].intAwayScore}", Toast.LENGTH_SHORT).show()
        }

        val prefs = context?.getSharedPreferences("NAME_LOGO", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonName = prefs?.getString("DATA_LIST_NAME", null)
        val jsonLogo = prefs?.getString("DATA_LIST_LOGO", null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type

        val dataListName = gson.fromJson<ArrayList<String>>(jsonName,type)
        val dataListLogo = gson.fromJson<ArrayList<String>>(jsonLogo, type)

        val idHome = dataListName?.indexOf(dataList[position].strHomeTeam)
        val idAway = dataListName?.indexOf(dataList[position].strAwayTeam)

        val imageHomeTeamLogo = holder.homeTeamLogo
        val imageAwayTeamLogo = holder.awayTeamLogo

        Picasso.get().load(dataListLogo?.get(idHome!!)).fit().into(imageHomeTeamLogo)

        Picasso.get().load(dataListLogo?.get(idAway!!)).fit().into(imageAwayTeamLogo)
    }
}