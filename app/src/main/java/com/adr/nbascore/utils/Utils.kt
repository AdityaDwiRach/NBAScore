package com.adr.nbascore.utils

import android.content.Context
import android.os.Build
import android.util.Log
import com.adr.nbascore.adapter.RVAdapterListTeam

class Utils {
    fun checkFavoritedTeam(context: Context?, teamShortName: String): Boolean{
        val sp = context?.getSharedPreferences(RVAdapterListTeam.FAVORITE_TEAM, Context.MODE_PRIVATE)
        val keys: Map<String, *> = sp?.all!!
        var teamFavorited = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            keys.forEach { (t, _) ->
                if (t == teamShortName){
                    Log.i("Testiiing", "team ini sudah di favorite")
                    teamFavorited = true
                }
            }
        }
        return teamFavorited
    }
}