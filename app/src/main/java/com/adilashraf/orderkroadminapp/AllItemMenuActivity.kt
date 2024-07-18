package com.adilashraf.orderkroadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkroadminapp.adapter.AllItemsMenuAdapter
import com.adilashraf.orderkroadminapp.databinding.ActivityAllItemMenuBinding
import com.adilashraf.orderkroadminapp.model.AddItemsModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class AllItemMenuActivity : AppCompatActivity() {

    private var itemsList = ArrayList<AddItemsModel>()
    private lateinit var databaseRef: DatabaseReference
    private val binding: ActivityAllItemMenuBinding by lazy {
        ActivityAllItemMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initialing Database
        databaseRef = Firebase.database.reference

        getData()

        binding.imgback.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        val itemRef = databaseRef.child("menu")
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val item = data.getValue(AddItemsModel::class.java)
                        itemsList.add(item!!)
                    }
                    setAdapter(itemsList)
                }
            }
            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun setAdapter(itemsList: ArrayList<AddItemsModel>) {
        val adapter = AllItemsMenuAdapter(itemsList, this@AllItemMenuActivity)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)

    }
}
