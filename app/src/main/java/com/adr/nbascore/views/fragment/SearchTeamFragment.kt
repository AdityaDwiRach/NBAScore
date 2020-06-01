package com.adr.nbascore.views.fragment

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
import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterSearch
import com.adr.nbascore.model.api.APILastGameInterface
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.last_game.LastGame
import com.adr.nbascore.model.last_game.LastGameL
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.presenter.ISearchFragmentPresenter
import com.adr.nbascore.presenter.SearchFragmentPresenter
import com.adr.nbascore.views.ISearchFragmentView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchTeamFragment : Fragment(), ISearchFragmentView {
    //TODO implement rxjava for getting API data

    lateinit var searchView: SearchView
    private val rVAdapterSearch by lazy { RVAdapterSearch() }
    private lateinit var recycleViewSearch: RecyclerView
    lateinit var listView: ListView
    var searchString: CharSequence = ""
    var teamNameSearch: String? = ""
    private var teamName: Array<String> = arrayOf()
    private lateinit var listArrayAdapter: ArrayAdapter<String>
    private val presenter by lazy { SearchFragmentPresenter(this) }


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, container, false)
        searchView = view.findViewById(R.id.search_view)
        recycleViewSearch = view.findViewById(R.id.recycle_view_search)
        listView = view.findViewById(R.id.search_suggest)

        recycleViewSearch.layoutManager = LinearLayoutManager(context)
        recycleViewSearch.adapter = rVAdapterSearch

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    progress_bar_search.visibility = View.VISIBLE
                    searchString = search_view.query
//                    getSpecificTeam(searchString.toString())
                    presenter.getSearchTeam(searchString.toString())
                } else {
                    Toast.makeText(context, "Search field must not be empty", Toast.LENGTH_LONG)
                        .show()
                }
                searchView.setQuery("", false)
                searchView.clearFocus()
                return false
            }
        })
        return view
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progress_bar_search.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar_search.visibility = View.GONE
    }

    override fun onSuccessSearchTeam(
        teamName: String,
        teamDescription: String,
        teamBadge: String,
        teamLogo: String
    ) {
        val teamBadgeSearch = view?.findViewById<ImageView>(R.id.team_badge_search)
        val teamLogoSearch = view?.findViewById<ImageView>(R.id.team_logo_search)

        team_name_search.text = teamName
        team_desc_search.text = teamDescription

        Picasso.get().load(teamBadge).fit().into(teamBadgeSearch)
        Picasso.get().load(teamLogo).fit().into(teamLogoSearch)
    }

    override fun onSuccessLastGame(last5Games: List<LastGame>, teamName: String) {
        rVAdapterSearch.setListData(last5Games)
        rVAdapterSearch.setSearchTeam(teamName)
        rVAdapterSearch.refreshData()
    }
}