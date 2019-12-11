package com.adr.nbascore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list_team, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.recycle_view)
        recycleView.layoutManager = LinearLayoutManager(context)

        val apiServices = APIClient.client.create(APITeamInterface::class.java)
        val call = apiServices.getDataTeam()

        call.enqueue(object : Callback<TeamL> {
            override fun onFailure(call: Call<TeamL>, t: Throwable) {
                progress_bar.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
                val dataList:List<Team> = response.body()?.teams!!
                rVAdapterListTeam = RVAdapterListTeam(context!!, dataList)
                recycleView.adapter = rVAdapterListTeam
                progress_bar.visibility = View.GONE
            }

        })
        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }
}