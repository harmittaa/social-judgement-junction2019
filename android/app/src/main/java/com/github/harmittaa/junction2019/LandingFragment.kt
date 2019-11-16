package com.github.harmittaa.junction2019

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.harmittaa.junction2019.models.Basket
import com.github.harmittaa.junction2019.models.Totals
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_landing.*
import java.math.BigDecimal
import java.math.RoundingMode


class LandingFragment : Fragment(), View.OnClickListener {
    var db: FirebaseFirestore? = null
    private var adapter: FirestoreRecyclerAdapter<Basket, TransactionViewHolder>? = null
    private var userKarma: Long = 0L
    private var spentBigDecimal: BigDecimal? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val vieww = layoutInflater.inflate(R.layout.fragment_landing, container, false)
        db = FirebaseFirestore.getInstance()
        return vieww
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Totals.notifyThis2 = this
    }
    override fun onStart() {
        super.onStart()
        populateOveralls()
        setupRecyclerView()
        adapter?.startListening()
    }

    private fun populateOveralls() {
        val state = lifecycle.currentState
        if (!state.isAtLeast(Lifecycle.State.CREATED)) {return}
        db?.collection("baskets")?.whereEqualTo("user", userId)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var spent = 0.0
                    for (document in task.result!!) {

                        val basket = document.toObject(Basket::class.java)
                        spent += basket.price
                        userKarma += basket.karma
                    }
                    spentBigDecimal = BigDecimal(spent).setScale(2, RoundingMode.HALF_EVEN)

                    amount_karma.text = "$userKarma"
                    amount_spent.text = "$spentBigDecimal €"
                    if (userKarma > 0) {
                        amount_karma.setTextColor(context?.resources?.getColor(R.color.green)!!)

                    } else {
                        amount_karma.setTextColor(context?.resources?.getColor(R.color.red)!!)

                    }

                } else {
                    Log.w("this", "Error getting documents.", task.exception)
                }
            }
    }


    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
        Totals.notifyThis2 = null
    }

    private fun setupRecyclerView() {
        transactions_list.adapter
        transactions_list.layoutManager = LinearLayoutManager(context!!);

        val query = db?.collection("baskets")?.orderBy("timestamp", Query.Direction.DESCENDING)
            ?.whereEqualTo("user", userId)

        val options = FirestoreRecyclerOptions.Builder<Basket>()
        val req = options.setQuery(query!!, Basket::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<Basket, TransactionViewHolder>(req) {
            override fun onBindViewHolder(
                holder: TransactionViewHolder,
                position: Int,
                model: Basket
            ) {
                holder.setBasket(model, position)
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): TransactionViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transaction_item, parent, false)
                view.setOnClickListener(this@LandingFragment)
                return TransactionViewHolder(view)
            }
        }
        transactions_list.adapter = adapter
    }

    override fun onClick(view: View?) {
        val p0 = view!!
        val basketPojo = adapter?.getItem(p0.tag as Int)
        Totals.basket = basketPojo!!
        val basket = adapter?.snapshots?.getSnapshot(p0.tag as Int)
        Totals.basketDoc = basket
        Totals.hasVoting = false

        val mainActivity = activity as MainActivity
        mainActivity.showFragment(PopupFragment())
        return
    }
}