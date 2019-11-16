package com.github.harmittaa.junction2019

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.junction2019.models.Basket


class TransactionViewHolder(private val transactionRow: View) : RecyclerView.ViewHolder(
    transactionRow
) {

    fun setBasket(basket: Basket) {

        // "${model.price} €
        val price: TextView = transactionRow.findViewById(R.id.transaction_price)
        price.text = "${basket.price} €"
        val location: TextView = transactionRow.findViewById(R.id.transaction_location)
        location.text = "${basket.store}"
        val date: TextView = transactionRow.findViewById(R.id.transaction_date)
        date.text = "${basket.price}"
        val cs: ConstraintLayout = transactionRow.findViewById(R.id.karma_points)
        val karma = cs.findViewById<TextView>(R.id.karma_points_tv)
        karma.text = "${basket.karma}"

    }
}