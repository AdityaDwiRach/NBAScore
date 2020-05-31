package com.adr.nbascore.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adr.nbascore.R

class FavoriteTeamFragment : Fragment() {
    //TODO implement like search fragment, calling api using the team name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val stringBuilder = StringBuilder()
//        val sp = context?.getSharedPreferences(RVAdapterListTeam.FAVORITE_TEAM, Context.MODE_PRIVATE)
//        val keys: Map<String, *> = sp?.all!!
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            keys.forEach { (t, a) ->
//                Log.d(this.javaClass.simpleName, "key = $t, value = $a")
//            }
//        }
//        Log.d(this.javaClass.simpleName, "======================")
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }
}
