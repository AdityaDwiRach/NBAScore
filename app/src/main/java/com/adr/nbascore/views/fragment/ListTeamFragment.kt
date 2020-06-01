package com.adr.nbascore.views.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adr.nbascore.R
import com.adr.nbascore.adapter.RVAdapterListTeam
import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.presenter.ListTeamFragmentPresenter
import com.adr.nbascore.views.IListTeamFragmentView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_team.*

class ListTeamFragment : Fragment(), IListTeamFragmentView {
    //TODO add team detail fragment from list all team and display some api response

    private val rVAdapterListTeam by lazy { RVAdapterListTeam() }
    private val presenter by lazy { ListTeamFragmentPresenter(this) }
    lateinit var recycleView:RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var disposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list_team, container, false)

        recycleView = view.findViewById(R.id.recycle_view_list_team)
        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = rVAdapterListTeam

        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh_list_team)
        swipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
//            getData()
            presenter.getListTeam()
        }

//        getData()
        presenter.getListTeam()

        return view
//        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        swipeToRefresh.isRefreshing = true
//        progress_bar_list_team.visibility = View.VISIBLE
//        progress_bar_list_team.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        swipeToRefresh.isRefreshing = false
//        progress_bar_list_team.visibility = View.GONE
//        progress_bar_list_team.visibility = View.GONE
    }

    override fun onSuccessListTeam(dataList: List<Team>) {
        rVAdapterListTeam.setListData(dataList)
        rVAdapterListTeam.refreshData()
    }

//    private fun getData(){
//        swipeToRefresh.isRefreshing = true
//        val apiServices = APIClient().client().create(APITeamInterface::class.java)
//        val call = apiServices.getDataAllTeam()
//
//        call.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(getDataObserver())
//    }
//
//    private fun getDataObserver(): Observer<TeamL>{
//        return object : Observer<TeamL> {
//            override fun onComplete() {
//                Log.d(this.javaClass.simpleName, "Get API success")
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                disposable.add(d)
//            }
//
//            override fun onNext(t: TeamL) {
//                swipeToRefresh.isRefreshing = false
//                val dataList:List<Team> = t.teams
//                rVAdapterListTeam = RVAdapterListTeam(activity?.applicationContext, dataList)
//                recycleView.adapter = rVAdapterListTeam
//                progress_bar_list_team.visibility = View.GONE
//            }
//
//            override fun onError(e: Throwable) {
//                Log.e(this.javaClass.simpleName, "onError : $e")
//                swipeToRefresh.isRefreshing = false
//                progress_bar_list_team.visibility = View.GONE
//                Toast.makeText(context, "Failed, please try again another minute", Toast.LENGTH_LONG).show()
//            }
//
//        }
//    }
}