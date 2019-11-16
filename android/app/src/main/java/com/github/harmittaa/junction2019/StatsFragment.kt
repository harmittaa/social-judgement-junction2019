package com.github.harmittaa.junction2019

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.harmittaa.junction2019.models.Basket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_stats.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.firebase.firestore.Query
import kotlin.random.Random




var userId = "AYD1wNUgj31szhrVr1At"

open class StatsFragment : Fragment() {
    var db: FirebaseFirestore? = null
    var baskets = mutableListOf<Basket>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val vieww = layoutInflater.inflate(R.layout.fragment_stats, container, false)
        db = FirebaseFirestore.getInstance()
        return vieww
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //barchart.

        val query = db?.collection("baskets")?.orderBy("timestamp", Query.Direction.DESCENDING)
            ?.whereEqualTo("user", userId)
        db?.collection("baskets")?.orderBy("timestamp", Query.Direction.DESCENDING)
            ?.whereEqualTo("user", userId)?.get()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        baskets.add(document.toObject(Basket::class.java))
                        Log.d("", document.id + " => " + document.data)
                    }
                    populateGraph()
                } else {
                    Log.w("", "Error getting documents.", task.exception)
                }
            }
    }

    private fun populateGraph() {
        val prices = mutableListOf<BarEntry>()
        val karmas = mutableListOf<BarEntry>()
        for (x in 0 until baskets.size) {
            if (x % 2 == 0) {
                prices.add(BarEntry(x.toFloat(), (baskets[x].price + Random.nextInt(0, 100)).toFloat()))
                karmas.add(BarEntry(x.toFloat()+1, (baskets[x].price + Random.nextInt(0, 100)).toFloat()))
            } else {
                prices.add(BarEntry(x.toFloat()+1, (baskets[x].price + Random.nextInt(0, 100)).toFloat()))
                karmas.add(BarEntry(x.toFloat(), (baskets[x].price + Random.nextInt(0, 100)).toFloat()))
            }
            //karmas.add(BarEntry(baskets[x].karma.toFloat(), x.toFloat()))
        }
        // 48, 215, 174
        val dataSet = BarDataSet(prices, "baskets")
        dataSet.setColor(Color.rgb(48, 214, 174))


        val dataSet2 = BarDataSet(karmas, "karmas")
        dataSet.setColor(Color.rgb(230, 95, 64))

        val barData = BarData(dataSet, dataSet2)
        barchart!!.data = barData
    }

    private fun generateBarData(dataSets: Int, range: Float, count: Int): BarData {

        val sets = mutableListOf<IBarDataSet>()

        for (i in 0 until dataSets) {

            val entries = mutableListOf<BarEntry>()

            for (j in 0 until count) {
                entries.add(BarEntry(j.toFloat(), (Math.random() * range).toFloat() + range / 4))
            }

            val ds = BarDataSet(entries, "label")
            ds.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            sets.add(ds)
        }

        val d = BarData(sets)
        return d
    }

}