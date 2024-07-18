package com.adilashraf.orderkroadminapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.adilashraf.orderkroadminapp.databinding.ActivityAddMenuBinding
import com.adilashraf.orderkroadminapp.model.AddItemsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class AddMenuActivity : AppCompatActivity() {

    private lateinit var itemName: String
    private lateinit var itemPrice: String
    private var itemImageUri: Uri? = null
    private lateinit var itemDescription: String
    private lateinit var itemIngredients: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    private val binding: ActivityAddMenuBinding by lazy {
        ActivityAddMenuBinding.inflate(layoutInflater)
    }

    private var pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        run {
            if (uri != null) {
                binding.addImage1.setImageURI(uri)
                itemImageUri = uri
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initializing Auth
        auth = Firebase.auth
        // initializing Database
        databaseRef = Firebase.database.getReference("menu").ref
        // initializing Storage
        storageRef = Firebase.storage.reference

        binding.imgback.setOnClickListener {
            finish()
        }

        binding.addImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.addImage1.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.addItem.setOnClickListener {
            itemName = binding.editItemName.text.toString().trim()
            itemPrice = binding.editItemPrice.text.toString().trim()
            itemDescription = binding.editItemShortDescription.text.toString().trim()
            itemIngredients = binding.editItemIngredients.text.toString().trim()

            if (itemName.isBlank() || itemPrice.isBlank() || itemDescription.isBlank() || itemIngredients.isBlank()) {
                Toast.makeText(this, "Please Fill all Details", Toast.LENGTH_SHORT).show()
            } else {
                uploadData()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun uploadData() {
        val itemKey = databaseRef.push().key
        if (itemImageUri != null) {
            val imgRef = storageRef.child("menu_images/${itemKey}.jpg")
            val uploadImg = imgRef.putFile(itemImageUri!!)
            uploadImg.addOnSuccessListener {
                imgRef.downloadUrl.addOnSuccessListener { downloadImgUrl ->
                    if (downloadImgUrl != null) {
                        val item = AddItemsModel(
                            itemName,
                            itemPrice,
                            downloadImgUrl.toString(),
                            itemDescription,
                            itemIngredients,
                        )
                        databaseRef.child(itemKey.toString()).setValue(item)
                    }
                }.addOnFailureListener {}
            }
        } else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
        }
    }
}
