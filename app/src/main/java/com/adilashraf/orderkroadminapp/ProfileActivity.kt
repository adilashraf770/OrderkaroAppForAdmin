package com.adilashraf.orderkroadminapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivityProfileBinding
import com.adilashraf.orderkroadminapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Suppress("SameParameterValue")
class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var uid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialing Auth
        auth = Firebase.auth
        // Initialing database
        databaseRef = Firebase.database.reference
        // Initialing uid
        uid = auth.currentUser!!.uid


        var isEnabled = false
        binding.apply {
            editName.isEnabled = isEnabled
            editResteurantName.isEnabled = isEnabled
            editAddress.isEnabled = isEnabled
            editEmail.isEnabled = isEnabled
            editPhone.isEnabled = isEnabled
            editPassword.isEnabled = isEnabled

            editProfile.setOnClickListener {
                isEnabled = !isEnabled
                editName.isEnabled = isEnabled
                editResteurantName.isEnabled = isEnabled
                editAddress.isEnabled = isEnabled
                editEmail.isEnabled = isEnabled
                editPhone.isEnabled = isEnabled
                editPassword.isEnabled = isEnabled
                if (isEnabled) {
                    editName.requestFocus()
                }
            }

            imgback.setOnClickListener {
                finish()
            }

            saveInfo.setOnClickListener {
                val name = editName.text.toString().trim()
                val restaurantName = editResteurantName.text.toString().trim()
                val email = editEmail.text.toString().trim()
                val address = editAddress.text.toString().trim()
                val phone = editPhone.text.toString().trim()
                val password = editPassword.text.toString().trim()

                if (name.isBlank() || email.isBlank() || address.isBlank() || phone.isBlank() || password.isBlank()) {
                    showToast("Fill all Details")
                 } else {
                    updateProfile(name, restaurantName, email, address, phone, password)
                }
            }
        }

        getUserDetails()

    }

    private fun updateProfile(
        name: String,
        restaurantName: String,
        email: String,
        address: String,
        phone: String,
        password: String,

    ) {

        val uid = auth.currentUser?.uid!!
        val itemRef = databaseRef.child("admin").child(uid)
        val userData = mapOf(
            "name" to name,
            "nameOfRestaurant" to restaurantName,
            "email" to email,
            "address" to address,
            "phone" to phone,
            "password" to password,
        )

        itemRef.setValue(userData as Map<String, Any>)
        showToast("Profile Updated")


    }

    private fun getUserDetails() {
        databaseRef.child("admin").child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    val name = user!!.name
                    val nameOfResteurant = user!!.nameOfRestaurant
                    val email = user.email
                    val address = user.address
                    val phone = user.phone
                    val password = user.password
                    setData(name,nameOfResteurant, email, address, phone, password)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }


    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


    private fun setData(
        name: String?,
        nameOfResteurant: String?,
        email: String?,
        address: String?,
        phone: String?,
        password: String?,
    ) {
        binding.apply {
            editName.setText(name.toString())
            editResteurantName.setText(nameOfResteurant.toString())
            editEmail.setText(email.toString())
            editAddress.setText(address.toString())
            editPhone.setText(phone.toString())
            editPassword.setText(password.toString())
        }
    }
}