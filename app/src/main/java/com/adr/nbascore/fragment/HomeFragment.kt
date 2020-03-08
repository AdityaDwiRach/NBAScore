package com.adr.nbascore.fragment

import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adr.nbascore.api.APIClient
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterCurrentMatch
import com.adr.nbascore.api.APICurrentMatchInterface
import com.adr.nbascore.api.APITeamInterface
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.current_match.CurrentMatchL
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.rxjavatest.Task
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {//TODO this fragment relatively safe from crash, need to copy the code to another fragment

    lateinit var rVAdapterCurrentMatch: RVAdapterCurrentMatch
    var dataListTeamName: ArrayList<String>? = null
    var dataListTeamLogo: ArrayList<String>? = null
    lateinit var recycleView: RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar
    private lateinit var disposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recycleView = view.findViewById(R.id.recycle_view_home)
        recycleView.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.progress_bar)

        disposable = CompositeDisposable()

        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh_home)

        swipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
            getMatchData()
            getLogoData()
        }

        getMatchData()
        getLogoData()

        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }

    fun getMatchData(){
        swipeToRefresh.isRefreshing = true
        val apiServicesMatch = APIClient.client?.create(APICurrentMatchInterface::class.java)
        val callMatch = apiServicesMatch?.getDataCurrentMatch()

        callMatch?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getMatchDataObserver())

//            enqueue(object : Callback<CurrentMatchL> {
//            override fun onFailure(call: Call<CurrentMatchL>, t: Throwable) {
//                swipeToRefresh.isRefreshing = false
//                progressBar.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<CurrentMatchL>, response: Response<CurrentMatchL>) {
//                swipeToRefresh.isRefreshing = false
//                val dataList:List<CurrentMatch> = response.body()?.events!!
//                rVAdapterCurrentMatch = RVAdapterCurrentMatch(activity?.applicationContext, dataList)
//                recycleView.adapter = rVAdapterCurrentMatch
//                progressBar.visibility = View.GONE
//            }
//
//        })
    }

    private fun getMatchDataObserver(): Observer<CurrentMatchL> {
        return object : Observer<CurrentMatchL> {
            override fun onComplete() {
                Log.i("Testiiing", "Get API success")
            }

            override fun onSubscribe(d: Disposable) {
                Log.i("Testiiing", "onSubscribe : called")
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Log.e("Testiiing", "onError : $e")
                swipeToRefresh.isRefreshing = false
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onNext(t: CurrentMatchL) {
                Log.i("Testiiing", "onNext : ${Thread.currentThread().name}")
                swipeToRefresh.isRefreshing = false
                val dataList:List<CurrentMatch> = t.events
                rVAdapterCurrentMatch = RVAdapterCurrentMatch(activity?.applicationContext, dataList)
                recycleView.adapter = rVAdapterCurrentMatch
                progressBar.visibility = View.GONE
            }

        }
    }

    fun getLogoData(){//TODO make it inside rxjava
        swipeToRefresh.isRefreshing = true
        val apiServicesLogo = APIClient.client?.create(APITeamInterface::class.java)
        val callLogo = apiServicesLogo?.getDataAllTeam()

        callLogo?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getLogoDataObserver())
//        .enqueue(object : Callback<TeamL> {
//            override fun onFailure(call: Call<TeamL>, t: Throwable) {
//                swipeToRefresh.isRefreshing = false
//                progressBar.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<TeamL>, response: Response<TeamL>) {
//                Log.i("Testiiiiiing", "get api response oke")
//                swipeToRefresh.isRefreshing = false
//                dataListTeamName = ArrayList()
//                dataListTeamLogo = ArrayList()
//                val dataListTeam:List<Team> = response.body()?.teams!!
//                val iterator = dataListTeam.listIterator()
//
//                for (item in iterator ){
//                    dataListTeamName!!.add(item.strTeam)
//                    dataListTeamLogo!!.add(item.strTeamBadge)
//                }
//
//                val prefs = context?.getSharedPreferences("NAME_LOGO", MODE_PRIVATE)
//                val editor = prefs?.edit()
//                val gson = Gson()
//                val jsonName = gson.toJson(dataListTeamName)
//                val jsonLogo = gson.toJson(dataListTeamLogo)
//                editor?.putString("DATA_LIST_NAME", jsonName)
//                editor?.putString("DATA_LIST_LOGO", jsonLogo)
//                editor?.apply()
//
//                progressBar.visibility = View.GONE
//            }
//
//        })
    }

    private fun getLogoDataObserver(): Observer<TeamL> {
        return object : Observer<TeamL> {
            override fun onComplete() {
                Log.i("Testiiing", "Get API success")
            }

            override fun onSubscribe(d: Disposable) {
                Log.i("Testiiing", "onSubscribe : called")
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Log.e("Testiiing2", "onError : $e")
                swipeToRefresh.isRefreshing = false
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
            }

            override fun onNext(t: TeamL) {
                Log.i("Testiiing", "onNext : ${Thread.currentThread().name}")
                swipeToRefresh.isRefreshing = false
                dataListTeamName = ArrayList()
                dataListTeamLogo = ArrayList()
                val dataListTeam:List<Team> = t.teams
                val iterator = dataListTeam.listIterator()

                for (item in iterator ){
                    dataListTeamName!!.add(item.strTeam)
                    dataListTeamLogo!!.add(item.strTeamBadge)
                }

                val prefs = context?.getSharedPreferences("NAME_LOGO", MODE_PRIVATE)
                val editor = prefs?.edit()
                val gson = Gson()
                val jsonName = gson.toJson(dataListTeamName)
                val jsonLogo = gson.toJson(dataListTeamLogo)
                editor?.putString("DATA_LIST_NAME", jsonName)
                editor?.putString("DATA_LIST_LOGO", jsonLogo)
                editor?.apply()

                progressBar.visibility = View.GONE
            }

        }
    }
    
}