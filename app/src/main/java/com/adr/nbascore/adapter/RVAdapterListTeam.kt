package com.adr.nbascore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.list_team.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_team.view.*

class RVAdapterListTeam(private var context: Context?, private var dataList: List<Team>): RecyclerView.Adapter<RVAdapterListTeam.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var teamLogo: ImageView = itemView.team_logo
        val teamName: TextView = itemView.team_name
        val stadiumName: TextView = itemView.stadium_name
        val stadiumLocation: TextView = itemView.stadium_location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageTeamLogo = holder.teamLogo

        Picasso.get().load(dataList[position].strTeamBadge).fit().into(imageTeamLogo)

        holder.teamName.text = dataList[position].strTeam
        holder.stadiumName.text = dataList[position].strStadium
        holder.stadiumLocation.text = dataList[position].strStadiumDescription
    }


}