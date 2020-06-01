package com.adr.nbascore.presenter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.adr.nbascore.adapter.RVAdapterCurrentMatch
import com.adr.nbascore.views.IHomeFragmentView
import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.model.api.APICurrentMatchInterface
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.current_match.CurrentMatch
import com.adr.nbascore.model.current_match.CurrentMatchL
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeFragmentPresenter(val homeFragmentView: IHomeFragmentView, val context: Context) :
    IHomeFragmentPresenter {
    val disposable = CompositeDisposable()

    override fun getCurrentMatch() {
        homeFragmentView.showLoading()
        val apiServicesMatch = APIClient().client().create(APICurrentMatchInterface::class.java)
        val callMatch = apiServicesMatch.getDataCurrentMatch()

        callMatch.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe((object : Observer<CurrentMatchL> {
                override fun onComplete() {
                    homeFragmentView.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: CurrentMatchL) {
                    val dataList: List<CurrentMatch> = t.events
                    homeFragmentView.onSuccessData(dataList)
                }

                override fun onError(e: Throwable) {
                    homeFragmentView.onError("Failed, please try again another minute")
                    homeFragmentView.hideLoading()
                }

            }))
    }

    override fun setLogoData() {
        val apiServicesLogo = APIClient().client().create(APITeamInterface::class.java)
        val callLogo = apiServicesLogo.getDataAllTeam()

//        homeFragmentView.showLoading()

        callLogo.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<TeamL> {
                override fun onComplete() {
//                    homeFragmentView.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: TeamL) {
                    val dataListTeamName = ArrayList<String>()
                    val dataListTeamLogo = ArrayList<String>()
                    val dataListTeam: List<Team> = t.teams
                    val iterator = dataListTeam.listIterator()

                    for (item in iterator) {
                        dataListTeamName.add(item.strTeam)
                        dataListTeamLogo.add(item.strTeamBadge)
                    }

                    homeFragmentView.onSuccessTeamLogo(dataListTeamName, dataListTeamLogo)

//                    val prefs = context?.getSharedPreferences("NAME_LOGO", Context.MODE_PRIVATE)
//                    val editor = prefs?.edit()
//                    val gson = Gson()
//                    val jsonName = gson.toJson(dataListTeamName)
//                    val jsonLogo = gson.toJson(dataListTeamLogo)
//                    editor?.putString("DATA_LIST_NAME", jsonName)
//                    editor?.putString("DATA_LIST_LOGO", jsonLogo)
//                    editor?.apply()
                }

                override fun onError(e: Throwable) {
                    homeFragmentView.onError("Failed, please try again another minute")
                    homeFragmentView.hideLoading()
                }

            })
    }
}