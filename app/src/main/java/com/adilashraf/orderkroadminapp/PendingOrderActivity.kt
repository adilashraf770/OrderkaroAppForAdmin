package com.adilashraf.orderkroadminapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkroadminapp.adapter.PendingOrderAdapter
import com.adilashraf.orderkroadminapp.databinding.ActivityPendingOrderBinding
import com.adilashraf.orderkroadminapp.model.OrderPlaceDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {

    val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private val orderPendingList = mutableListOf<OrderPlaceDetailsModel>()
    private val listOfUserNames = mutableListOf<String>()
    private val listOfOrderTotalPrices = mutableListOf<String>()
    private val listOfOrderImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initializing auth
        auth = Firebase.auth
        // initializing database
        databaseRef = Firebase.database.reference

        getData()

        binding.imgback.setOnClickListener {
            finish()
        }

    }

    private fun getData() {

        databaseRef.child("order_details")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val item = data.getValue(OrderPlaceDetailsModel::class.java)
                            orderPendingList.add(item!!)
                        }
                        addItemToRecyclerView()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

    }

    private fun addItemToRecyclerView() {
        for (data in orderPendingList) {
            data.name!!.let { listOfUserNames.add(it) }
            data.totalPrice!!.let { listOfOrderTotalPrices.add(it) }
            data.foodImages.filterNot { it.isEmpty() }.forEach {
                listOfOrderImages.add(it)
            }
        }
        setAdapter(listOfUserNames, listOfOrderTotalPrices, listOfOrderImages)
    }


    private fun setAdapter(
        listOfUserNames: MutableList<String>,
        listOfOrderTotalPrices: MutableList<String>,
        listOfOrderImages: MutableList<String>,
    ) {
        val adapter = PendingOrderAdapter(
            listOfUserNames,
            listOfOrderTotalPrices,
            listOfOrderImages,
            this,
            this
        )
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@PendingOrderActivity)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
    }

    override fun onItemClickListener(position: Int) {
        val orderDetails = orderPendingList[position]
        val i = Intent(this, OrderDetailsActivity::class.java)
        i.putExtra("orderDetails", orderDetails)
        startActivity(i)
    }



    override fun onItemAcceptClickListener(position: Int) {
        val orderDetails = orderPendingList[position]
        val pushKey = orderPendingList[position].pushKey!!

        databaseRef.child("complete_orders").child(pushKey).setValue(orderDetails)
            .addOnSuccessListener {
                updateOrderAcceptStatus(position)
             }


    }

    private fun updateOrderAcceptStatus(position: Int) {
        val uid = orderPendingList[position].uid!!
        val pushKey = orderPendingList[position].pushKey!!

        val orderCompleteRef = databaseRef.child("complete_orders").child(pushKey)
        orderCompleteRef.child("orderAccepted").setValue(true)
        orderCompleteRef.child("paymentReceived").setValue(true)

        val buyHistoryRef =
            databaseRef.child("user").child(uid).child("order_history").child(pushKey)
        buyHistoryRef.child("orderAccepted").setValue(true)
        buyHistoryRef.child("paymentReceived").setValue(true)

        removeItemFromDatabase(pushKey)

    }



    private fun removeItemFromDatabase(pushKey: String) {
        val orderDetailsRef = databaseRef.child("order_details")
        orderDetailsRef.child(pushKey).removeValue()
            .addOnCompleteListener {
                Toast.makeText(this, "Order is Dispatched", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Order is not Dispatched", Toast.LENGTH_SHORT).show()
            }
    }


}