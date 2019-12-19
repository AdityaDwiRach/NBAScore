package com.adr.nbascore.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adr.learnjson2.adapter.RVAdapterListTeam
import com.adr.learnjson2.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.api.APITeamInterface
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import kotlinx.android.synthetic.main.fragment_list_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListTeamFragment : Fragment() {

    lateinit var rVAdapterListTeam: RVAdapterListTeam
    lateinit var recycleView:RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list_team, container, false)
        recycleView = view.findViewById(R.id.recycle_view_list_team)
        recycleView.layoutManager = LinearLayoutManager(context)

        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh_list_team)

        swipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
            getData()
        }

        getData()

        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }

    fun getData(){
        swipeToRefresh.isRefreshing = true
        val apiServices = APIClient.client.create(APITeamInterface::class.java)
        val call = apiServices.getDataAllTeam()

        call.enqueue(object : Callback<TeamL> {
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                swipeToRefresh.isRefreshing = false
                progress_bar_list_team.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                swipeToRefresh.isRefreshing = false
                val dataList:List<Team> = response.body()?.teams!!
                rVAdapterListTeam = RVAdapterListTeam(context!!, dataList)
                recycleView.adapter = rVAdapterListTeam
                progress_bar_list_team.visibility = View.GONE
            }

        })
    }
}