package com.adr.nbascore.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adr.nbascore.R
import com.adr.nbascore.views.fragment.FavoriteTeamFragment
import com.adr.nbascore.views.fragment.HomeFragment
import com.adr.nbascore.views.fragment.ListTeamFragment
import com.adr.nbascore.views.fragment.SearchTeamFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //TODO implement dark mode feature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()

        btm_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container, HomeFragment()).commit()
                R.id.search_menu -> supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container, SearchTeamFragment()).commit()
                R.id.favorite_menu -> supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container, FavoriteTeamFragment()).commit()
                R.id.team_menu -> supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container, ListTeamFragment()).commit()
            }
            true
        }
    }
}
