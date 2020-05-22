package com.adr.nbascore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adr.nbascore.fragment.FavoriteTeamFragment
import com.adr.nbascore.fragment.HomeFragment
import com.adr.nbascore.fragment.ListTeamFragment
import com.adr.nbascore.fragment.SearchTeamFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //TODO implement dark mode feature
    //TODO make icon for this app
    //TODO add favorite fragment to display the favorite team. get the list from list team


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()

        btm_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()
                R.id.search_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, SearchTeamFragment()).commit()
                R.id.favorite_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, FavoriteTeamFragment()).commit()
                R.id.team_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, ListTeamFragment()).commit()
            }
            true
        }
    }
}
