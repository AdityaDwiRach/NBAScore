package com.adr.nbascore.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    //TODO display data from last game API

    lateinit var searchView: SearchView
    lateinit var rVAdapterSearch: RVAdapterSearch
    lateinit var recycleViewSearch: RecyclerView
    var searchString: CharSequence = ""
    var specificTeamId: String? = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, container, false)
        searchView = view.findViewById(R.id.search_view)
        recycleViewSearch = view.findViewById(R.id.recycle_view_search)
        recycleViewSearch.layoutManager = LinearLayoutManager(context)
//        progress_bar_search.visibility = View.GONE

        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                progress_bar_search.visibility = View.VISIBLE
                searchString = search_view.query
                getSpecificTeam(searchString.toString())
                getLast5Game(134880)
                searchView.setQuery("",false)
                searchView.clearFocus()
                return false
            }
        })
        return view
    }

    fun getSpecificTeam(teamName: String){
        val apiServices = APIClient.client?.create(APITeamInterface::class.java)
        val call = apiServices?.getDataTeam(teamName)

        call?.enqueue(object : Callback<TeamL> {
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                progress_bar_search.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                val dataListTeam = response.body()?.teams
                val teamBadgeSearch = view?.findViewById<ImageView>(R.id.team_badge_search)
                val teamLogoSearch = view?.findViewById<ImageView>(R.id.team_logo_search)

                team_name_search.text = dataListTeam?.get(0)?.strTeam
                team_desc_search.text = dataListTeam?.get(0)?.strDescriptionEN

                Picasso.get().load(dataListTeam?.get(0)?.strTeamBadge).fit().into(teamBadgeSearch)
                Picasso.get().load(dataListTeam?.get(0)?.strTeamLogo).fit().into(teamLogoSearch)

                specificTeamId = dataListTeam?.get(0)?.idTeam

                progress_bar_search.visibility = View.GONE
            }

        })
    }

    fun getLast5Game(teamId: Int){
        val apiServices = APIClient.client?.create(APILastGameInterface::class.java)
        val call = apiServices?.getDataLastGame(teamId)

        call?.enqueue(object : Callback<LastGameL> {
            override fun onFailure(call: Call<LastGameL>, t: Throwable) {
                progress_bar_search.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<LastGameL>, response: Response<LastGameL>) {
                val dataListLastGame = response.body()?.results

                if (dataListLastGame == null){
                    Log.i("Testiiing", "null bro")
                } else {
                    Log.i("Testiiing", "not null")
                }
                tv_last5games.visibility = View.VISIBLE
//                val test = dataListLastGame?.get(4)?.strEvent
//                Log.i("Testiiing", test.toString())
//                Toast.makeText(context, test, Toast.LENGTH_SHORT).show()
//
                rVAdapterSearch = RVAdapterSearch(activity?.applicationContext, dataListLastGame)
                recycleViewSearch.adapter = rVAdapterSearch
//
                progress_bar_search.visibility = View.GONE
            }

        })
    }

//    fun onCreateOptionsMenu(menu: Menu): Boolean {
//        getMenuInflater().inflate(R.menu.search_action_bar_menu, menu)
//        val activity: Activity = this
//
//
//        searchView.setQueryHint("Search for users...")
//        val columNames =
//            arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
//        val viewIds = intArrayOf(R.id.text1)
//        val adapter: CursorAdapter = SimpleCursorAdapter(
//            this,
//            R.layout.simple_list_item_1, null, columNames, viewIds
//        )
//        searchView.setSuggestionsAdapter(adapter)
//        searchView.setOnSuggestionListener(getOnSuggestionClickListener())
//        searchView.setOnQueryTextListener(getOnQueryTextListener(activity, adapter))
//        return true
//    }
//
//
//    private fun getOnQueryTextListener(
//        activity: Activity,
//        adapter: CursorAdapter
//    ): OnQueryTextListener? {
//        return object : OnQueryTextListener() {
//            fun onQueryTextSubmit(s: String?): Boolean {
//                return false
//            }
//
//            fun onQueryTextChange(s: String): Boolean {
//                if (s.length < 2) {
//                    return false
//                }
//                val observable: Observable<Map<String, User>> =
//                    SearchMiddleware.searchForUsers(s, activity)
//                observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(getRequestObserver(adapter))
//                return true
//            }
//        }
//    }
//
//
//    private fun getRequestObserver(adapter: CursorAdapter): Observer? {
//        return object : Observer<Map<String?, User?>?>() {
//            fun onSubscribe(d: Disposable?) {}
//            fun onNext(users: Map<String, User>) {
//                val cursor: Cursor = createCursorFromResult(users)
//                adapter.swapCursor(cursor)
//            }
//
//            fun onError(e: Throwable?) {}
//            fun onComplete() {}
//        }
//    }
//
//
//    private fun getOnSuggestionClickListener(): SearchView.OnSuggestionListener? {
//        return object : OnSuggestionListener() {
//            fun onSuggestionSelect(i: Int): Boolean {
//                return false
//            }
//
//            fun onSuggestionClick(index: Int): Boolean {
//                return true
//            }
//        }
//    }
//
//
//    private fun createCursorFromResult(users: Map<String, User>): Cursor {
//        val menuCols = arrayOf(
//            BaseColumns._ID,
//            SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA
//        )
//        val cursor = MatrixCursor(menuCols)
//        var counter = 0
//        for (username in users.keys) {
//            val user: User? = users[username]
//            cursor.addRow(arrayOf<Any>(counter, user.getUsername(), user.getUsername()))
//            counter++
//        }
//        return cursor
//    }
}