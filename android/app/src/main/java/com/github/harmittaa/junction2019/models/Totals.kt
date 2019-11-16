package com.github.harmittaa.junction2019.models

import com.github.harmittaa.junction2019.MainActivity
import java.math.BigDecimal
import java.math.RoundingMode

class Totals() {
    companion object {
        var toNotify: MainActivity? = null
        var totalSpent = 0.0
        var totalKarma = 0
        fun addToTotal(value: Double) {
            totalSpent += value
        }

        fun addToTotalKarma(value: Int) {
            totalKarma += value
        }

        fun notifyListener() {
            //toNotify?.dataFetched()
        }

        fun getTotalSpent(): BigDecimal = BigDecimal(totalSpent).setScale(2, RoundingMode.HALF_EVEN)
    }

}