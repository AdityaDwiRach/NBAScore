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
import com.adr.nbascore.adapter.RVAdapterCurrentMatch
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.presenter.HomeFragmentPresenter
import com.adr.nbascore.views.IHomeFragmentView


class HomeFragment : Fragment(), IHomeFragmentView {//TODO this fragment relatively safe from crash, need to copy the code to another fragment
    //TODO change passing logo data, not by sharedpreference
    private val rVAdapterCurrentMatch by lazy { RVAdapterCurrentMatch() }
    lateinit var recycleView: RecyclerView
    lateinit var swipeToRefresh: SwipeRefreshLayout
    private val presenter by lazy { HomeFragmentPresenter(this, context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recycleView = view.findViewById(R.id.recycle_view_home)
        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh_home)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = rVAdapterCurrentMatch

        swipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
            presenter.getCurrentMatch()
            presenter.setLogoData()
        }

        presenter.getCurrentMatch()
        presenter.setLogoData()

        return view
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        swipeToRefresh.isRefreshing = true
    }

    override fun hideLoading() {
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