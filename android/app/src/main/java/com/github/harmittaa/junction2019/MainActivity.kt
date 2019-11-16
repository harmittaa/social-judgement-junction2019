package com.github.harmittaa.junction2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(listener)
        supportActionBar?.hide()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LandingFragment(), "weather").commit()

    }

    private val listener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.landing -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LandingFragment(), "wallofsame").commit()
                blockerView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
            R.id.wall_of_shame -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, WallOfShameFragment(), "wallofsame").commit()
                blockerView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
            R.id.stats -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, StatsFragment(), "wallofsame").commit()
                blockerView.setBackgroundColor(resources.getColor(R.color.babyblue))
            }

            else -> Log.d("this", "else")
        }
        true
    }


    fun showFragment(fragment: SuperBottomSheetFragment) {
        fragment.show(supportFragmentManager, "TAG")
    }
}
