package com.adr.nbascore.presenter

import com.adr.nbascore.model.api.APIClient
import com.adr.nbascore.model.api.APILastGameInterface
import com.adr.nbascore.model.api.APITeamInterface
import com.adr.nbascore.model.last_game.LastGameL
import com.adr.nbascore.model.list_team.TeamL
import com.adr.nbascore.views.ISearchFragmentView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchFragmentPresenter(val iSearchFragmentView: ISearchFragmentView) :
    ISearchFragmentPresenter {

    private val disposable = CompositeDisposable()

    override fun getSearchTeam(teamName: String) {
        getSpecificTeam(teamName)
    }

    private fun getSpecificTeam(teamName: String) {
        iSearchFragmentView.showLoading()
        val apiServices = APIClient().client().create(APITeamInterface::class.java)
        val call = apiServices.getDataTeam(teamName)

        call.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<TeamL> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: TeamL) {
                    val dataListTeam = t.teams
                    val teamNameResponse = dataListTeam[0].strTeam
                    val teamId = dataListTeam[0].idTeam

                    iSearchFragmentView.onSuccessSearchTeam(teamNameResponse, dataListTeam[0].strDescriptionEN, dataListTeam[0].strTeamBadge, dataListTeam[0].strTeamLogo)

                    getLast5Game(teamId, teamNameResponse)
                }

                override fun onError(e: Throwable) {
                    iSearchFragmentView.onError("Failed, please try again another minute")
                }

            })
    }

    private fun getLast5Game(teamId: String, teamName: String) {
        val apiServices = APIClient().client().create(APILastGameInterface::class.java)
        val call = apiServices.getDataLastGame(teamId)

        call.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<LastGameL> {

                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: LastGameL) {
                    val dataListLastGame = t.results

                    iSearchFragmentView.hideLoading()
                    iSearchFragmentView.onSuccessLastGame(dataListLastGame, teamName)
                }

                override fun onError(e: Throwable) {
                    iSearchFragmentView.hideLoading()
                    iSearchFragmentView.onError("Failed, please try again another minute")
                }

            })
    }
}