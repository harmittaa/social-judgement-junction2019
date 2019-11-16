package com.github.harmittaa.junction2019

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.harmittaa.junction2019.models.Basket
import com.github.harmittaa.junction2019.models.Totals
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wall_of_shame.*

class WallOfShameFragment : Fragment() {
    var db: FirebaseFirestore? = null
    private var adapter: FirestoreRecyclerAdapter<Basket, WallOfShameViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val vieww = layoutInflater.inflate(R.layout.fragment_wall_of_shame, container, false)
        db = FirebaseFirestore.getInstance()
        return vieww
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onResume() {
        super.onResume()
        Totals.notifyThis = this
    }

    override fun onPause() {
        super.onPause()
        Totals.notifyThis = null
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    private fun setupRecyclerView() {
        Totals.notifyThis = this
        history_list.adapter
        history_list.layoutManager = LinearLayoutManager(context!!);
        val query = db?.collection("baskets")?.orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Basket>()
        val req = options.setQuery(query!!, Basket::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<Basket, WallOfShameViewHolder>(req) {
            override fun onBindViewHolder(
                holder: WallOfShameViewHolder,
                position: Int,
                model: Basket
            ) {
                holder.setBasket(model, position)
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): WallOfShameViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.wall_item2, parent, false)
                val viewHolder = WallOfShameViewHolder(view)
                viewHolder.setListener(this@WallOfShameFragment)
                return WallOfShameViewHolder(view)
            }
        }
        history_list.adapter = adapter
    }

    fun onClick(p0: View) {
        val basketPojo = adapter?.getItem(p0.tag as Int)
        Totals.basket = basketPojo!!
        val basket = adapter?.getSnapshots()?.getSnapshot(p0.tag as Int)
        Totals.basketDoc = basket

        val id = p0.id

        if (id == R.id.middleClicker) {
            val mainActivity = activity as MainActivity
            mainActivity.showFragment(PopupFragment())
            return
        }


        val updateTo = when (id) {
            R.id.thumbs_up -> 1
            R.id.positiveClicker -> 1
            R.id.thumbs_down -> -1
            R.id.negativeClicker -> -1
            else -> return
        }


        if (basket != null) {
            db?.collection("baskets")?.document(basket.id)
                ?.update(mapOf("karma" to (basketPojo!!.karma + updateTo)))
        }


    }


    /*


    Map<String, Object> user = new HashMap<>();
user.put("first", "Ada");
user.put("last", "Lovelace");
user.put("born", 1815);

// Add a new document with a generated ID
db.collection("users")
        .add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
     */
}