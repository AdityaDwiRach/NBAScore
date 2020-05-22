package com.adr.nbascore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adr.nbascore.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterSearch
import com.adr.nbascore.api.APILastGameInterface
import com.adr.nbascore.api.APITeamInterface
import com.adr.nbascore.model.last_game.LastGameL
import com.adr.nbascore.model.list_team.TeamL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_search_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchTeamFragment: Fragment() {
    //TODO implement rxjava for getting API data

    lateinit var searchView: SearchView
    lateinit var rVAdapterSearch: RVAdapterSearch
    lateinit var recycleViewSearch: RecyclerView
    lateinit var listView: ListView
    var searchString: CharSequence = ""
    var teamNameSearch: String? = ""
    private var teamName: Array<String> = arrayOf()
    private lateinit var listArrayAdapter: ArrayAdapter<String>


    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, container, false)
        searchView = view.findViewById(R.id.search_view)
        recycleViewSearch = view.findViewById(R.id.recycle_view_search)
        listView = view.findViewById(R.id.search_suggest)

        recycleViewSearch.layoutManager = LinearLayoutManager(context)

        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    progress_bar_search.visibility = View.VISIBLE
                    searchString = search_view.query
                    getSpecificTeam(searchString.toString())
                } else {
                    Toast.makeText(context, "Search field must not be empty", Toast.LENGTH_LONG).show()
                }
                searchView.setQuery("",false)
                searchView.clearFocus()
                return false
            }
        })
        return view
    }

    private fun getSpecificTeam(teamName: String){
        val apiServices = APIClient().client().create(APITeamInterface::class.java)
        val call = apiServices.getDataTeam(teamName)

        call.enqueue(object : Callback<TeamL> {
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                progress_bar_search.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                if (response.isSuccessful){
                    val dataListTeam = response.body()?.teams
                    val teamBadgeSearch = view?.findViewById<ImageView>(R.id.team_badge_search)
                    val teamLogoSearch = view?.findViewById<ImageView>(R.id.team_logo_search)
                    val teamNameResponse = dataListTeam?.get(0)?.strTeam

                    team_name_search.text = teamNameResponse
                    team_desc_search.text = dataListTeam?.get(0)?.strDescriptionEN

                    Picasso.get().load(dataListTeam?.get(0)?.strTeamBadge).fit().into(teamBadgeSearch)
                    Picasso.get().load(dataListTeam?.get(0)?.strTeamLogo).fit().into(teamLogoSearch)

                    val teamId = dataListTeam?.get(0)?.idTeam

                    getLast5Game(teamId.toString(), teamNameResponse.toString())
                }

                progress_bar_search.visibility = View.GONE
            }

        })
    }

    private fun getLast5Game(teamId: String, teamName: String){
        val apiServices = APIClient().client().create(APILastGameInterface::class.java)
        val call = apiServices.getDataLastGame(teamId)

        call.enqueue(object : Callback<LastGameL> {
            override fun onFailure(call: Call<LastGameL>, t: Throwable) {
                progress_bar_search.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<LastGameL>, response: Response<LastGameL>) {

                if (response.isSuccessful){
                    val dataListLastGame = response.body()?.results

                    if (dataListLastGame == null){
                        Log.i("Testiiing", "null bro")
                    } else {
                        Log.i("Testiiing", "not null")
                    }
                    tv_last5games.visibility = View.VISIBLE

                    rVAdapterSearch = RVAdapterSearch(activity?.applicationContext, dataListLastGame, teamName)
                    recycleViewSearch.adapter = rVAdapterSearch
                }

                progress_bar_search.visibility = View.GONE
            }

        })
    }
}