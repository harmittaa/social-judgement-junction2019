package com.github.harmittaa.junction2019

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_stats.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class StatsFragment : Fragment() {
    var db: FirebaseFirestore? = null
    private var chart: BarChart? = null

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
        barchart!!.description.isEnabled = false;
        barchart!!.setData(generateBarData(1, 20000f, 12));
        val l = barchart!!.getLegend()

        val leftAxis = barchart!!.getAxisLeft()
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        barchart!!.getAxisRight().isEnabled = false

        val xAxis = barchart!!.getXAxis()
        xAxis.isEnabled = false

    }

    protected fun generateBarData(dataSets: Int, range: Float, count: Int): BarData {

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