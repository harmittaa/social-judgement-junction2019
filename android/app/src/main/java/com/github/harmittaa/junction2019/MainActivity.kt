package com.github.harmittaa.junction2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

import android.util.Log
import com.github.harmittaa.junction2019.models.Basket
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.harmittaa.junction2019.models.Totals
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.*
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_landing.*
import java.math.BigDecimal
import java.math.RoundingMode


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
            R.id.landing -> Log.d("this", "Landing.")
            R.id.stats -> Log.d("this", "stats")
            R.id.wall -> Log.d("this", "wall")

            else -> Log.d("this", "else")
        }
        true
    }


    /*
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "tag",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w("", "Error adding document", e) }

                    db.collection("baskets")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val city = document.toObject(Basket::class.java)
                        Log.d("this", document.id + " => " + document.data)
                    }

                } else {
                    Log.w("this", "Error getting documents.", task.exception)
                }
            }

*/

    /*

            db!!.collection("baskets")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val city = document.toObject(Basket::class.java)
                        Log.d("this", document.id + " => " + document.data)
                        db!!
                            .collection("baskets")
                            .document(document.id)
                            .update(mapOf<String, Long>("timestamp" to getTimeStamp())).addOnSuccessListener { documentReference ->
                                Log.d(
                                    "tag",
                                    "DocumentSnapshot added with ID: "
                                )
                            }
                            .addOnFailureListener { e -> Log.w("", "Error adding document", e) }

                    }

                } else {
                    Log.w("this", "Error getting documents.", task.exception)
                }
            }

    }

    private fun getTimeStamp(): Long {
        return 1573800000 + Random.nextLong(10000, 99999)
        //return "${Random.nextInt(10000, 99999)}"
    }

     */

}
