package com.adilashraf.orderkroadminapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkroadminapp.databinding.ActivityMenuItemBinding
import com.bumptech.glide.Glide

class MenuItemActivity : AppCompatActivity() {
    val binding: ActivityMenuItemBinding by lazy {
        ActivityMenuItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("itemName")
        val image = intent.getStringExtra("itemImage")
        val description = intent.getStringExtra("itemDescription")
        val ingredients = intent.getStringExtra("itemIngredients")

        binding.apply {
            val uri = Uri.parse(image)
            foodName.text = name
            foodDescription.text = description
            foodIngredients.text = ingredients
            Glide.with(this@MenuItemActivity).load(uri).into(foodImage)

            backImage.setOnClickListener {
                finish()
            }
        }



    }
}