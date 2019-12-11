package com.adr.nbascore.fragment

import android.os.Bundle
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
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.current_match.CurrentMatchL
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var rVAdapterCurrentMatch: RVAdapterCurrentMatch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recycleView = view.findViewById<RecyclerView>(R.id.recycle_view)
        recycleView.layoutManager = LinearLayoutManager(context)

        val apiServices = APIClient.client.create(APICurrentMatchInterface::class.java)
        val call = apiServices.getDataCurrentMatch()

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
        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }
}