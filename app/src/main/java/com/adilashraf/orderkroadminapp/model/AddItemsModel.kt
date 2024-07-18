package com.adilashraf.orderkroadminapp.model

data class AddItemsModel(
    val itemName: String? = null,
    val itemPrice: String? = null,
    val itemImage: String? = null,
    val itemDescription: String? = null,
    val itemIngredients: String? = null,
    val quantity: Int? = null,
)
