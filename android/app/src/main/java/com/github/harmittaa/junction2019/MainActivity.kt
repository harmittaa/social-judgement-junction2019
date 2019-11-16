package com.github.harmittaa.junction2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import android.util.Log
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
            R.id.landing -> supportFragmentManager.beginTransaction()
                .replace(R.id.container, LandingFragment(), "landing").commit()
            R.id.wall_of_shame -> supportFragmentManager.beginTransaction()
                .replace(R.id.container, WallOfShameFragment(), "wallofsame").commit()
            R.id.stats -> Log.d("this", "wall")

            else -> Log.d("this", "else")
        }
        true
    }
}
