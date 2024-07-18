package com.adilashraf.orderkroadminapp


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkroadminapp.adapter.OrderDispatchAdapter
import com.adilashraf.orderkroadminapp.databinding.ActivityOrderDispatchBinding
import com.adilashraf.orderkroadminapp.model.OrderPlaceDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class OrderDispatchActivity : AppCompatActivity() {
    val binding: ActivityOrderDispatchBinding by lazy {
        ActivityOrderDispatchBinding.inflate(layoutInflater)
    }
    var orderDetailsList = ArrayList<OrderPlaceDetailsModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initialing Auth
        auth = Firebase.auth
       // initialing Database
        databaseRef = Firebase.database.reference

        binding.imgback.setOnClickListener {
            finish()
        }

        getData()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getData() {
        databaseRef.child("complete_orders")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (data in snapshot.children){
                            val item = data.getValue(OrderPlaceDetailsModel::class.java)
                            orderDetailsList.add(item!!)
                        }
                        addDataToRecyclerView(orderDetailsList)
                    }
                 }

                override fun onCancelled(error: DatabaseError) {}

            })

    }

    private fun addDataToRecyclerView(orderDetailsList: ArrayList<OrderPlaceDetailsModel>) {
        val userNames = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (data in orderDetailsList){
           userNames.add(data.name!!)
            moneyStatus.add(data.isPaymentReceived)
        }

        setAdapter(userNames, moneyStatus)


    }

    private fun setAdapter(userNames: MutableList<String>, moneyStatus: MutableList<Boolean>) {
        val adapter = OrderDispatchAdapter(userNames, moneyStatus, this )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)

    }
}