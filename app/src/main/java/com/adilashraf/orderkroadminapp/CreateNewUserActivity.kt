package com.adilashraf.orderkroadminapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivityCreateNewUserBinding
import com.adilashraf.orderkroadminapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateNewUserActivity : AppCompatActivity() {
    private val binding: ActivityCreateNewUserBinding by lazy {
        ActivityCreateNewUserBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var name: String? = null
    private var restaurantName: String? = null
    private var email: String? = null
    private var address: String? = null
    private var phone: String? = null
    private var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialing Auth
        auth = Firebase.auth
        // Initialing database
        databaseRef = Firebase.database.reference

        binding.apply {


            imgBack.setOnClickListener {
                finish()
            }

            btnCreateAccount.setOnClickListener {
                name = editName.text.toString().trim()
                restaurantName = editNameOfRestaurant.text.toString().trim()
                email = editEmail.text.toString().trim()
                address = editAddress.text.toString().trim()
                phone = editPhone.text.toString().trim()
                password = editPassword.text.toString().trim()

                if (name!!.isBlank() || email!!.isBlank() || address!!.isBlank() || phone!!.isBlank() || password!!.isBlank()) {
                    showToast("Fill all Details")
                } else {
                    createAdmin(email!!, password!!)
                }
            }
        }

    }

    private fun createAdmin(
        email: String,
        password: String,
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user  = auth.currentUser!!
                saveData(user)
                showToast("New Admin is Created Successfully")
            }
        }.addOnFailureListener {
            showToast("Failed to Create Admin")

        }

    }

    private fun saveData(user: FirebaseUser) {
        binding.apply {
            name = editName.text.toString().trim()
            restaurantName = editNameOfRestaurant.text.toString().trim()
            email = editEmail.text.toString().trim()
            address = editAddress.text.toString().trim()
            phone = editPhone.text.toString().trim()
            password = editPassword.text.toString().trim()
        }

        val uid = user.uid
        val userData = UserModel(name, restaurantName, email, password, address, phone )
        try {
            databaseRef.child("admin").child(uid).setValue(userData)

        }catch(e:Error) {}
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}