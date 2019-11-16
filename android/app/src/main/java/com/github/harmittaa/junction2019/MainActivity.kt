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


class MainActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    private var adapter: FirestoreRecyclerAdapter<Basket, TransactionViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    private fun setupRecyclerView() {
        transactions_list.adapter
        transactions_list.layoutManager = LinearLayoutManager(this);


        val query = db?.collection("baskets")

        val options = FirestoreRecyclerOptions.Builder<Basket>()
        var req = options.setQuery(query!!, Basket::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<Basket, TransactionViewHolder>(req) {
            override fun onBindViewHolder(
                holder: TransactionViewHolder,
                position: Int,
                model: Basket
            ) {
                holder.setBasket(model)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transaction_item, parent, false)
                return TransactionViewHolder(view)
            }
        }
        transactions_list.adapter = adapter
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

}
