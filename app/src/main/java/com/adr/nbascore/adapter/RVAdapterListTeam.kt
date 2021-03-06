package com.adr.nbascore.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.R
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_team.view.*


class RVAdapterListTeam: RecyclerView.Adapter<RVAdapterListTeam.ViewHolder>(), IListTeamAdapterModel, IListTeamAdapterView {

    private var context: Context? = null
    private var dataList: List<Team> = ArrayList()

    companion object {
        const val FAVORITE_TEAM = "favoriteteam"
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var teamLogo: ImageView = itemView.team_logo
        val teamName: TextView = itemView.team_name
        val stadiumName: TextView = itemView.stadium_name
        val stadiumLocation: TextView = itemView.stadium_location
        val favorite: ImageView = itemView.img_team_favorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, parent, false))
    }

    override fun getItemCount(): Int = getSizeData()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataListBind = getListData()
        val imageTeamLogo = holder.teamLogo
        val favoriteKey = dataListBind[position].strTeamShort
        val sharedPreferences = context?.getSharedPreferences(FAVORITE_TEAM, Context.MODE_PRIVATE)

        Picasso.get().load(dataListBind[position].strTeamBadge).fit().into(imageTeamLogo)

        holder.teamName.text = dataListBind[position].strTeam
        holder.stadiumName.text = dataListBind[position].strStadium
        holder.stadiumLocation.text = dataListBind[position].strStadiumLocation

        if (Utils().checkFavoritedTeam(context, favoriteKey)){
            holder.favorite.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_unfavorite)
        }

        holder.favorite.setOnClickListener{
            val mConstantState: Drawable.ConstantState? =
                context?.resources?.getDrawable(R.drawable.ic_unfavorite, context?.theme)?.constantState
            val mHolderConstantState = holder.favorite.drawable.constantState

            if (mHolderConstantState == mConstantState){
                if (Utils().checkFavoritedTeam(context, favoriteKey)){
                    Toast.makeText(context, "Team are already in your favorite tab", Toast.LENGTH_SHORT).show()
                } else {
                    val editor = sharedPreferences?.edit()
                    editor?.putString(favoriteKey, dataList[position].strTeam)
                    editor?.apply()

                    holder.favorite.setImageResource(R.drawable.ic_favorite)
                }
            } else {
                if (Utils().checkFavoritedTeam(context, favoriteKey)){
                    val editor = sharedPreferences?.edit()
                    editor?.remove(favoriteKey)
                    editor?.apply()

                    holder.favorite.setImageResource(R.drawable.ic_unfavorite)
                } else {
                    Toast.makeText(context, "Team is not in your favorite tab", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setListData(listData: List<Team>) {
        this.dataList = listData
    }

    override fun getSizeData(): Int {
        return dataList.size
    }

    override fun getListData(): List<Team> {
        return dataList
    }

    override fun refreshData() {
        notifyDataSetChanged()
    }
}