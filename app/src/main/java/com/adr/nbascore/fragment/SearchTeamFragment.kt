package com.adr.nbascore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.adr.learnjson2.adapter.RVAdapterListTeam
import com.adr.learnjson2.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.api.APITeamInterface
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import kotlinx.android.synthetic.main.fragment_list_team.*
import kotlinx.android.synthetic.main.fragment_search_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchTeamFragment: Fragment() {

    lateinit var searchView: SearchView
    var dataListTeamName: ArrayList<String>? = null
    var dataListTeam: List<Team>? = null
    var searchString: CharSequence = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, container, false)
        searchView = view.findViewById(R.id.search_view)
//        progress_bar_search.visibility = View.GONE

        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                progress_bar_search.visibility = View.VISIBLE
                searchString = search_view.query
                getSpecificTeam(searchString.toString())
                setSearchDataTeam()
                Toast.makeText(context, searchString, Toast.LENGTH_LONG).show()
                // task HERE
                return false
            }
        })
        return view
    }

    fun getSpecificTeam(teamName: String){
        val apiServices = APIClient.client.create(APITeamInterface::class.java)
        val call = apiServices.getDataTeam(teamName)

        call.enqueue(object : Callback<TeamL> {
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                progress_bar_search.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                dataListTeam = response.body()?.teams!!
                dataListTeamName = ArrayList()
                val iterator = dataListTeam!!.listIterator()

                for (item in iterator ){
                    dataListTeamName!!.add(item.strTeam)
                }

                progress_bar_search.visibility = View.GONE
            }

        })
    }

    fun setSearchDataTeam(){
        val idTeamName = dataListTeamName?.indexOf(searchString)
        team_name_search.text = dataListTeam?.get(idTeamName!!)?.strTeam
    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//
//        super.onCreate(savedInstanceState)
//    }

//    fun searchViewFun(){
//        search_view.setOnQueryTextListener(object : OnQueryTextListener {
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String): Boolean {
//                // task HERE
//                return false
//            }
//        })
//
//    }

//    fun getData(){
//        swipeToRefresh.isRefreshing = true
//        val apiServices = APIClient.client.create(APITeamInterface::class.java)
//        val call = apiServices.getDataAllTeam()
//
//        call.enqueue(object : Callback<TeamL> {
//            override fun onFailure(call: Call<TeamL>, t: Throwable) {
//                swipeToRefresh.isRefreshing = false
//                progress_bar.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
//                swipeToRefresh.isRefreshing = false
//                val dataList:List<Team> = response.body()?.teams!!
//                rVAdapterListTeam = RVAdapterListTeam(context!!, dataList)
//                recycleView.adapter = rVAdapterListTeam
//                progress_bar.visibility = View.GONE
//            }
//
//        })
//    }

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
//            fun onSuggestionClick(index: Int): Boolean { // TODO: handle suggestion item click
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