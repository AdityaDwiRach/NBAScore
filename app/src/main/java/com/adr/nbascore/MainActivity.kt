package com.adr.nbascore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adr.nbascore.fragment.HomeFragment
import com.adr.nbascore.fragment.ListTeamFragment
import com.adr.nbascore.fragment.SearchTeamFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()

//        btm_nav.setOnNavigationItemSelectedListener(navigationItemSelected)

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

//    private val navigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener{
//        when(it.itemId){
//            R.id.home_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()
////                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
//            R.id.search_menu -> Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
//            R.id.favorite_menu -> Toast.makeText(this, "Favorite clicked", Toast.LENGTH_SHORT).show()
//            R.id.team_menu -> supportFragmentManager.beginTransaction().replace(R.id.fl_container, ListTeamFragment()).commit()
////                Toast.makeText(this, "Team clicked", Toast.LENGTH_SHORT).show()
//        }
//        false
//    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var fragment: Fragment? = null
//        Log.i("Testiiiing", "navigation item clicked")
//
//        when(item.itemId){
//            R.id.team_menu -> {
//                fragment = ListTeamFragment()
//            }
//        }
//        btm_nav.setOnNavigationItemSelectedListener(navigationItemSelected)
//        return true
//    }
//
//    private fun loadFragment(fragment: Fragment?): Boolean {
//        if (fragment != null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_container, fragment)
//                .commit()
//            return true
//        }
//        return false
//    }
}
