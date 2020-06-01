package com.adr.nbascore.presenter

import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.list_team.Team
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.views.IListTeamFragmentView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListTeamFragmentPresenter(val iListTeamFragmentView: IListTeamFragmentView) :
    IListTeamFragmentPresenter {

    private val disposable = CompositeDisposable()

    override fun getListTeam() {
        iListTeamFragmentView.showLoading()
        val apiServices = APIClient().client().create(APITeamInterface::class.java)
        val call = apiServices.getDataAllTeam()

        call.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<TeamL> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: TeamL) {
                    val dataList: List<Team> = t.teams
                    iListTeamFragmentView.onSuccessListTeam(dataList)
                    iListTeamFragmentView.hideLoading()
                }

                override fun onError(e: Throwable) {
                    iListTeamFragmentView.onError("Failed, please try again another minute")
                    iListTeamFragmentView.hideLoading()
                }

            })
    }
}