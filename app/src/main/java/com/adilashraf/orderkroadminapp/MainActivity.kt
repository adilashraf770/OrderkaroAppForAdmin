package com.adilashraf.orderkroadminapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivityMainBinding
import com.adilashraf.orderkroadminapp.model.OrderPlaceDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initialing Auth
        auth = Firebase.auth
        // initialing Database
        databaseRef = Firebase.database.reference

        binding.apply {

            imgAddMenu.setOnClickListener {
                val i = Intent(this@MainActivity, AddMenuActivity::class.java)
                startActivity(i)
            }
            imgItemMenu.setOnClickListener {
                val i = Intent(this@MainActivity, AllItemMenuActivity::class.java)
                startActivity(i)
            }
            imgProfile.setOnClickListener {
                val i = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(i)
            }
            imgCreateNewUser.setOnClickListener {
                val i = Intent(this@MainActivity, CreateNewUserActivity::class.java)
                startActivity(i)
            }
            imgOrderDispatch.setOnClickListener {
                val i = Intent(this@MainActivity, OrderDispatchActivity::class.java)
                startActivity(i)
            }
            imgLogOut.setOnClickListener {
                Firebase.auth.signOut()
                val i = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(i)
                finish()
            }
            pendingOrder.setOnClickListener {
                val i = Intent(this@MainActivity, PendingOrderActivity::class.java)
                startActivity(i)
            }

            completeTxt.setOnClickListener {
                val i = Intent(this@MainActivity, OrderDispatchActivity::class.java)
                startActivity(i)
            }

        }

        getPendingOrders()
        getCompleteOrders()
        getWholeEarnings()
    }


    private fun getPendingOrders() {
        databaseRef.child("order_details")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var pendingOrderCount = 0
                    if (snapshot.exists()) {
                        pendingOrderCount = snapshot.childrenCount.toInt()
                    }
                    binding.pendingOrders.text = pendingOrderCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }


    private fun getWholeEarnings() {
        databaseRef.child("complete_orders")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfTotalPrice = mutableListOf<Int>()
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val item = data.getValue(OrderPlaceDetailsModel::class.java)
                            item!!.totalPrice!!.toIntOrNull().let { i ->
                                listOfTotalPrice.add(i!!)
                            }
                        }
                        binding.wholeEarning.text = listOfTotalPrice.sum().toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }


    private fun getCompleteOrders() {
        databaseRef.child("complete_orders")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var completeOrderCount = 0
                    if (snapshot.exists()) {
                        completeOrderCount = snapshot.childrenCount.toInt()
                    }
                    binding.completeOrders.text = completeOrderCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }


    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}