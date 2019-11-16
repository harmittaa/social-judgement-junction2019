package com.github.harmittaa.junction2019

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_popup.*
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.junction2019.models.Basket
import com.github.harmittaa.junction2019.models.Item
import com.github.harmittaa.junction2019.models.Totals
import com.google.firebase.firestore.FirebaseFirestore


class PopupFragment : SuperBottomSheetFragment() {
    private var basketPojo: Basket? = null
    private var shouldSetup = true
    private var db: FirebaseFirestore? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        shouldSetup = true
        db = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Totals.hasVoting) {
            hideVoting()
        }
        basketPojo = Totals.basket!!
        setupRecycler()
        setKarma(basketPojo!!.karma)
        positivewrapper.setOnClickListener {
            changeKarma(1)
        }
        negativewrap.setOnClickListener {
            changeKarma(-1)
        }
    }

    private fun hideVoting() {
        negativewrap.visibility = View.GONE
        positivewrapper.visibility = View.GONE
    }

    private fun setKarma(toValue: Int) {
        if (toValue < 0) {
            karma_wrapper.background = context!!.getDrawable(R.drawable.round_corners_red)
            popup_status.setImageDrawable(context!!.getDrawable(R.drawable.no_bueno))
        } else {
            karma_wrapper.background = context!!.getDrawable(R.drawable.round_corners_green)
            popup_status.setImageDrawable(context!!.getDrawable(R.drawable.success))
        }
        karma_amount.text = "$toValue"
    }

    private fun changeKarma(updateTo: Int) {
        setKarma(basketPojo!!.karma + updateTo)

        db!!.collection("baskets").document(Totals.basketDoc!!.id)
            .update(mapOf("karma" to (basketPojo!!.karma + updateTo)))
            .addOnSuccessListener {
                Log.d("SUCCESS", "SUCCESS")
                val oldKarma = basketPojo!!.karma
                val newKarma = oldKarma + updateTo
                basketPojo!!.karma = newKarma

            }.addOnFailureListener {
                Log.e("FAIL", "FAIL $it")
            }
    }

    private fun setupRecycler() {
        items_recycler.layoutManager = LinearLayoutManager(context!!)
        items_recycler.adapter = MyRecyclerViewAdapter(context!!, basketPojo!!.items)
    }

    override fun getCornerRadius() = 32f

    inner class MyRecyclerViewAdapter
    internal constructor(context: Context, private val mData: List<Item>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        private val mInflater: LayoutInflater = LayoutInflater.from(context)

        // inflates the row layout from xml when needed
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = mInflater.inflate(R.layout.purchase_item, parent, false)
            return ViewHolder(view)
        }

        // binds the data to the TextView in each row
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mData[position]
            holder.price.text = "${item.totalPrice} €"
            holder.name.text = "${item.name}"
        }

        // total number of rows
        override fun getItemCount(): Int {
            return mData.size
        }


        // stores and recycles views as they are scrolled off screen
        inner class ViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            internal var price: TextView
            internal var name: TextView

            init {
                price = itemView.findViewById(R.id.item_price)
                name = itemView.findViewById(R.id.item_name)
            }
        }

        // convenience method for getting data at click position
    }
}