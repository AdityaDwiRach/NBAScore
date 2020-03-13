package com.adr.nbascore

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adr.nbascore.fragment.HomeFragment
import com.adr.nbascore.fragment.ListTeamFragment
import com.adr.nbascore.fragment.SearchTeamFragment
import com.adr.nbascore.rxjavatest.DataSource
import com.adr.nbascore.rxjavatest.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //TODO implement rxjava for getting data from API
    //TODO implement dark mode feature
    //TODO make icon for this app


//    private lateinit var disposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()

        btm_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()
                R.id.search_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, SearchTeamFragment()).commit()
                R.id.favorite_menu -> Toast.makeText(this, "Favorite clicked", Toast.LENGTH_SHORT).show()
                R.id.team_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, ListTeamFragment()).commit()
            }
            true
        }
    }
}
