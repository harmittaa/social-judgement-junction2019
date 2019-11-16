package com.github.harmittaa.junction2019

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.junction2019.models.Basket
import com.github.harmittaa.junction2019.models.Totals
import kotlinx.android.synthetic.main.wall_item.view.comment
import kotlinx.android.synthetic.main.wall_item.view.thumbs_down
import kotlinx.android.synthetic.main.wall_item.view.thumbs_up
import kotlinx.android.synthetic.main.wall_item2.view.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone


class TransactionViewHolder(private val transactionRow: View) : RecyclerView.ViewHolder(
    transactionRow
) {

    fun setBasket(basket: Basket, position: Int) {
        transactionRow.tag = position
        transactionRow.setOnClickListener(Totals.notifyThis2)
        Totals.addToTotal(basket.price)
        Totals.addToTotalKarma(basket.karma)
        val dateTime = DateTime(basket.timestamp * 1000, DateTimeZone.UTC)

        val price: TextView = transactionRow.findViewById(R.id.transaction_price)
        price.text = "${basket.price} €"
        val location: TextView = transactionRow.findViewById(R.id.transaction_location)
        location.text = "${basket.store}"
        val date: TextView = transactionRow.findViewById(R.id.transaction_date)
        date.text =
            "${dateTime.dayOfMonth().getAsText()} ${dateTime.monthOfYear().getAsShortText()} "
        val time: TextView = transactionRow.findViewById(R.id.transaction_time)
        time.text = "${dateTime.hourOfDay().getAsText()}.${dateTime.minuteOfHour().getAsText()}"
        val cs: ConstraintLayout = transactionRow.findViewById(R.id.karma_points)
        val karma = cs.findViewById<TextView>(R.id.karma_points_tv)

        if (basket.karma > 0) {
            cs.background =
                transactionRow.context.resources.getDrawable(R.drawable.circle_outline_blue)
            karma.text = "${basket.karma}"
        } else {
            cs.background = transactionRow.context.resources.getDrawable(R.drawable.circle_outline)
            karma.text = "${basket.karma * -1}"
        }

    }
}

class WallOfShameViewHolder(private val wallItem: View) : RecyclerView.ViewHolder(
    wallItem
), View.OnClickListener {

    override fun onClick(p0: View) {
        Totals.notifyThis?.onClick(p0)
    }

    private var listener: WallOfShameFragment? = null

    fun setBasket(basket: Basket, position: Int) {
        Totals.addToTotal(basket.price)
        Totals.addToTotalKarma(basket.karma)
        wallItem.thumbs_down.setOnClickListener(this)
        wallItem.thumbs_down.tag = position
        wallItem.thumbs_up.setOnClickListener(this)
        wallItem.thumbs_up.tag = position
        wallItem.comment.setOnClickListener(this)
        wallItem.comment.tag = position
        wallItem.negativeClicker.tag = position
        wallItem.negativeClicker.setOnClickListener(this)
        wallItem.positiveClicker.tag = position
        wallItem.positiveClicker.setOnClickListener(this)
        wallItem.middleClicker.tag = position
        wallItem.middleClicker.setOnClickListener(this)

        // "${model.price} €
        val price: TextView = wallItem.findViewById(R.id.transaction_price)
        price.text = "${basket.price} €"
        val location: TextView = wallItem.findViewById(R.id.transaction_location)
        location.text = "${basket.store}"
        //val date: TextView = wallItem.findViewById(R.id.transaction_date)
        /*date.text =
            "${dateTime.dayOfMonth().getAsText()} ${dateTime.monthOfYear().getAsShortText()} "*/
        val cs: ConstraintLayout = wallItem.findViewById(R.id.karma_points)
        val karma = cs.findViewById<TextView>(R.id.karma_points_tv)

        if (basket.karma >= 0) {
            cs.background = wallItem.context.resources.getDrawable(R.drawable.circle_outline_blue)
            karma.text = "${basket.karma}"
        } else {
            cs.background = wallItem.context.resources.getDrawable(R.drawable.circle_outline)
            karma.text = "${basket.karma * -1}"
        }

    }

    fun setListener(wallOfShameFragment: WallOfShameFragment) {
        this.listener = wallOfShameFragment
    }
}