package com.adr.nbascore.views.fragment

import android.content.Context
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
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterCurrentMatch
import com.adr.nbascore.views.IHomeFragmentView
import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.presenter.HomeFragmentPresenter
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomeFragment : Fragment(), IHomeFragmentView {//TODO this fragment relatively safe from crash, need to copy the code to another fragment
    //TODO change passing logo data, not by sharedpreference
    private val rVAdapterCurrentMatch by lazy { RVAdapterCurrentMatch() }
    var dataListTeamName: ArrayList<String>? = null
    var dataListTeamLogo: ArrayList<String>? = null
    lateinit var recycleView: RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar
    private val presenter by lazy { HomeFragmentPresenter(this, context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recycleView = view.findViewById(R.id.recycle_view_home)
        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh_home)
        progressBar = view.findViewById(R.id.progress_bar_home)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = rVAdapterCurrentMatch

        swipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
            presenter.getCurrentMatch()
//            getLogoData()
            presenter.setLogoData()
        }

        presenter.getCurrentMatch()
//        getLogoData()
        presenter.setLogoData()

        return view
    }





//    private fun getMatchData(){
//        swipeToRefresh.isRefreshing = true
//        val apiServicesMatch = APIClient().client().create(APICurrentMatchInterface::class.java)
//        val callMatch = apiServicesMatch.getDataCurrentMatch()
//
//        callMatch.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(getMatchDataObserver())
//    }
//
//    private fun getMatchDataObserver(): Observer<CurrentMatchL> {
//        return object : Observer<CurrentMatchL> {
//            override fun onComplete() {
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                disposable.add(d)
//            }
//
//            override fun onError(e: Throwable) {
//                Log.e(TAG, "onError : $e")
//                swipeToRefresh.isRefreshing = false
//                progressBar.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onNext(t: CurrentMatchL) {
//                swipeToRefresh.isRefreshing = false
//                val dataList:List<CurrentMatch> = t.events
//                rVAdapterCurrentMatch = RVAdapterCurrentMatch(activity?.applicationContext, dataList)
//                recycleView.adapter = rVAdapterCurrentMatch
//                progressBar.visibility = View.GONE
//            }
//
//        }
//    }

//    private fun getLogoData(){
//        swipeToRefresh.isRefreshing = true
//        val apiServicesLogo = APIClient().client().create(APITeamInterface::class.java)
//        val callLogo = apiServicesLogo.getDataAllTeam()
//
//        callLogo.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(getLogoDataObserver())
//    }
//
//    private fun getLogoDataObserver(): Observer<TeamL> {
//        return object : Observer<TeamL> {
//            override fun onComplete() {
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                disposable.add(d)
//            }
//
//            override fun onError(e: Throwable) {
//                Log.e(TAG, "onError : $e")
//                swipeToRefresh.isRefreshing = false
//                if (fragmentContext != null){
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(fragmentContext, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onNext(t: TeamL) {
//                swipeToRefresh.isRefreshing = false
//                dataListTeamName = ArrayList()
//                dataListTeamLogo = ArrayList()
//                val dataListTeam:List<Team> = t.teams
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
//        }
//    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        Log.d("Testing", "loading show")
        swipeToRefresh.isRefreshing = true
        progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        Log.d("Testing", "loading hide")
        progressBar.visibility = View.GONE
        swipeToRefresh.isRefreshing = false
    }

    override fun onSuccessData(listData: List<CurrentMatch>) {
        Log.d("Testing", "masuk sini 4")
        rVAdapterCurrentMatch.setListData(listData)
        rVAdapterCurrentMatch.refreshData()
    }

    override fun onSuccessTeamLogo(listTeamName: List<String>, listTeamLogo: List<String>) {
        rVAdapterCurrentMatch.setListTeamName(listTeamName)
        rVAdapterCurrentMatch.setListTeamLogo(listTeamLogo)
        rVAdapterCurrentMatch.refreshData()
    }

}