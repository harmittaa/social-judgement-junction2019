package com.github.harmittaa.junction2019

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.junction2019.models.Basket
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.text.DateFormat
import java.util.*


class TransactionViewHolder(private val transactionRow: View) : RecyclerView.ViewHolder(
    transactionRow
) {

    fun setBasket(basket: Basket) {
        val dateTimeInUtc = DateTime("2011-07-19T18:23:20+0000", DateTimeZone.UTC)
        val formatter = DateFormat.getDateTimeInstance(
            DateFormat.SHORT,
            DateFormat.SHORT,
            Locale.getDefault()
        )

        val dateTime = DateTime(basket.timestamp * 1000, DateTimeZone.UTC)


        // "${model.price} €
        val price: TextView = transactionRow.findViewById(R.id.transaction_price)
        price.text = "${basket.price} €"
        val location: TextView = transactionRow.findViewById(R.id.transaction_location)
        location.text = "${basket.store}"
        val date: TextView = transactionRow.findViewById(R.id.transaction_date)
        date.text = "${dateTime.dayOfMonth().getAsText()} ${dateTime.monthOfYear().getAsShortText()} "
        val time: TextView = transactionRow.findViewById(R.id.transaction_time)
        time.text = "${dateTime.hourOfDay().getAsText()}.${dateTime.minuteOfHour().getAsText()}"
        val cs: ConstraintLayout = transactionRow.findViewById(R.id.karma_points)
        val karma = cs.findViewById<TextView>(R.id.karma_points_tv)

        if (basket.karma > 0) {
            cs.background = transactionRow.context.resources.getDrawable(R.drawable.circle_outline_blue)
            karma.text = "${basket.karma}"

            //cs.setBackgroundColor(transactionRow.context.getResources().getColor(R.color.niceWhite))
        } else {
            cs.background = transactionRow.context.resources.getDrawable(R.drawable.circle_outline)
            karma.text = "${basket.karma * -1}"
        }

    }
}