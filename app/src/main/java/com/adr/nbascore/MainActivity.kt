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

    private lateinit var disposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomeFragment()).commit()

//        val textView = findViewById<TextView>(R.id.tv_test)
//
//        disposable = CompositeDisposable()
//
//        val taskObservable = Observable
//            .fromIterable(DataSource.createTaskList())
//            .subscribeOn(Schedulers.io())
//            .filter {
//                Log.i("Testiiing", "test : ${Thread.currentThread().name}")
//                try {
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException){
//                    e.printStackTrace()
//                }
//                return@filter it.isComplete
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//
////        val taskObserver = Observer
//        taskObservable.subscribe(getObserver())


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

    private fun getObserver(): Observer<Task>{
        return object : Observer<Task>{
            override fun onComplete() {
                Log.i("Testiiing", "onComplete : called")
            }

            override fun onSubscribe(d: Disposable) {
                Log.i("Testiiing", "onSubscribe : called")
                disposable.add(d)
            }

            override fun onNext(t: Task) {
                Log.i("Testiiing", "onNext : ${Thread.currentThread().name}")
                Log.i("Testiiing", "onNext : ${t.description}")

            }

            override fun onError(e: Throwable) {
                Log.e("Testiiing", "onError : $e")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
