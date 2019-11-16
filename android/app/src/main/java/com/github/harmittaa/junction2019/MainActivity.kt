package com.github.harmittaa.junction2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.google.firebase.firestore.Query
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    private var adapter: FirestoreRecyclerAdapter<Basket, TransactionViewHolder>? = null
    private var userId = "AYD1wNUgj31szhrVr1At"
    private var userKarma: Long = 0L
    private var spentBigDecimal: BigDecimal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()
        populateOveralls()
        setupRecyclerView()
    }

    private fun populateOveralls() {
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
                        amount_karma.setTextColor(baseContext.resources.getColor(R.color.green))

                    } else {
                        amount_karma.setTextColor(baseContext.resources.getColor(R.color.red))

                    }

                } else {
                    Log.w("this", "Error getting documents.", task.exception)
                }
            }
    }


    override fun onStart() {
        super.onStart()
        Totals.toNotify = this
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        Totals.toNotify = null
        adapter?.stopListening()
    }

    private fun setupRecyclerView() {
        transactions_list.adapter
        transactions_list.layoutManager = LinearLayoutManager(this);


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
                holder.setBasket(model)
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): TransactionViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transaction_item, parent, false)
                return TransactionViewHolder(view)
            }
        }
        transactions_list.adapter = adapter
    }

    fun dataFetched() {
        amount_karma.text = "${Totals.totalKarma}"
        amount_spent.text = "${Totals.getTotalSpent()} €"
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
