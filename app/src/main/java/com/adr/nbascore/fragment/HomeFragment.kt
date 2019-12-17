package com.adr.nbascore.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adr.learnjson2.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterCurrentMatch
import com.adr.nbascore.api.APICurrentMatchInterface
import com.adr.nbascore.api.APITeamInterface
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.current_match.CurrentMatchL
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var rVAdapterCurrentMatch: RVAdapterCurrentMatch
    var dataListTeamName: ArrayList<String>? = null
    var dataListTeamLogo: ArrayList<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.recycle_view)
        recycleView.layoutManager = LinearLayoutManager(context)

        val apiServices = APIClient.client.create(APICurrentMatchInterface::class.java)
        val apiServicesLogo = APIClient.client.create(APITeamInterface::class.java)

        val call = apiServices.getDataCurrentMatch()
        val callLogo = apiServicesLogo.getDataTeam()

        callLogo.enqueue(object : Callback<TeamL> {
            //            val dataListTeamName = MutableList<String>
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                progress_bar.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                dataListTeamName = ArrayList()// cari cara pass data (arraylist) ke RVAdapter
                dataListTeamLogo = ArrayList()
                val dataListTeam:List<Team> = response.body()?.teams!!

//                dataListTeamLogo.clear()
//                dataListTeamName.clear()
                val iterator = dataListTeam.listIterator()
                for (item in iterator ){
                    dataListTeamName!!.add(item.strTeam)
                    dataListTeamLogo!!.add(item.strTeamLogo)
                }

//                val shared = context?.getSharedPreferences("NAME_LOGO", MODE_PRIVATE)
//
//                val editor: SharedPreferences.Editor? = shared?.edit()
//                val setTeamName: MutableSet<String> = HashSet()
//                val setTeamLogo: MutableSet<String> = HashSet()
//                setTeamName.addAll(dataListTeamName!!)
//                setTeamLogo.addAll(dataListTeamLogo!!)
//                editor?.putStringSet("DATA_LIST_NAME", setTeamName)
//                editor?.putStringSet("DATA_LIST_LOGO", setTeamLogo)
//                editor?.apply()

                val prefs = context?.getSharedPreferences("NAME_LOGO", MODE_PRIVATE)
//                    PreferenceManager.getDefaultSharedPreferences(activity)
                val editor = prefs?.edit()
                val gson = Gson()
                val jsonName = gson.toJson(dataListTeamName)
                val jsonLogo = gson.toJson(dataListTeamLogo)
                editor?.putString("DATA_LIST_NAME", jsonName)
                editor?.putString("DATA_LIST_LOGO", jsonLogo)
                editor?.apply()

                progress_bar.visibility = View.GONE

                for (i in dataListTeamName!!){
                    Log.i("testiiiiing", i)
                }
            }

        })

        call.enqueue(object : Callback<CurrentMatchL> {
            override fun onFailure(call: Call<CurrentMatchL>, t: Throwable) {
                progress_bar.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CurrentMatchL>, response: Response<CurrentMatchL>) {
                val dataList:List<CurrentMatch> = response.body()?.events!!
                rVAdapterCurrentMatch = RVAdapterCurrentMatch(context!!, dataList)
                recycleView.adapter = rVAdapterCurrentMatch
                progress_bar.visibility = View.GONE
            }

        })

//        callLogo.enqueue(object : Callback<TeamL> {
////            val dataListTeamName = MutableList<String>
//            override fun onFailure(call: Call<TeamL>, t: Throwable) {
//                progress_bar.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
//                dataListTeamName = ArrayList()
//                dataListTeamLogo = ArrayList()
//                val dataListTeam:List<Team> = response.body()?.teams!!
//
////                dataListTeamLogo.clear()
////                dataListTeamName.clear()
//                val iterator = dataListTeam.listIterator()
//                for (item in iterator ){
//                    dataListTeamName!!.add(item.strTeam)
//                    dataListTeamLogo!!.add(item.strTeamLogo)
//                }
//                progress_bar.visibility = View.GONE
//
//                for (i in dataListTeamName!!){
//                    Log.i("testiiiiing", i)
//                }
//            }
//
//        })
        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }
}