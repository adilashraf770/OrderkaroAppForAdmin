package com.adilashraf.orderkroadminapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.adilashraf.orderkroadminapp.databinding.ActivityOrderDetailsBinding
import com.adilashraf.orderkroadminapp.model.OrderPlaceDetailsModel as OrderPlaceDetailsModel1

@Suppress("DEPRECATION")
class OrderDetailsActivity : AppCompatActivity() {
    val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backImage.setOnClickListener {
            finish()
        }

        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val orderDetails = intent.getParcelableExtra<OrderPlaceDetailsModel1>("orderDetails")!!
        val userName = orderDetails.name
        val userAddress = orderDetails.address
        val phoneNumber = orderDetails.phone
        val totalPrice = orderDetails.totalPrice
        val names = orderDetails.foodNames as ArrayList
        val images = orderDetails.foodImages as ArrayList
        val prices = orderDetails.foodPrices as ArrayList

        setData(userName!!, userAddress!!, phoneNumber!!, totalPrice!!)


    }

    private fun setData(
        name: String,
        address: String,
        phoneNumber: String,
        totalPrice: String,
    ) {
        binding.apply {
            userName.text = name
            userAddress.text = address
            userPhone.text = phoneNumber
            foodPrice.text = totalPrice
        }


    }
}