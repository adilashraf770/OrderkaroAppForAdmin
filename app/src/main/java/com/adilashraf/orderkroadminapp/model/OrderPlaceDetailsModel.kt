package com.adilashraf.orderkroadminapp.model

import android.os.Parcel
import android.os.Parcelable

@Suppress("DEPRECATION")
class OrderPlaceDetailsModel() : Parcelable {
    var uid: String? = null
    var pushKey: String? = null
    var name: String? = null
    var address: String? = null
    var email: String? = null
    var phone: String? = null
    var totalPrice: String? = null
    var time: String? = null
    var foodNames: MutableList<String> = mutableListOf()
    var foodImages: MutableList<String> = mutableListOf()
    var foodIngredients: MutableList<String> = mutableListOf()
    var foodDescription: MutableList<String> = mutableListOf()
    var foodPrices: MutableList<String> = mutableListOf()
    var isOrderAccepted: Boolean = false
    var isPaymentReceived: Boolean = false


    constructor(parcel: Parcel) : this() {
        uid = parcel.readString().toString()
        name = parcel.readString().toString()
        address = parcel.readString().toString()
        email = parcel.readString().toString()
        phone = parcel.readString().toString()
        totalPrice = parcel.readString().toString()
        time = parcel.readString().toString()
        isOrderAccepted = parcel.readByte() != 0.toByte()
        isPaymentReceived = parcel.readByte() != 0.toByte()
    }

    constructor(
        uid: String,
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        totalPrice: String,
        time: String,
        foodNames: MutableList<String>,
        foodImages: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodDescription: MutableList<String>,
        foodPrices: MutableList<String>,
        orderAccepted: Boolean,
        paymentReceived: Boolean,
    ) : this() {
        this.uid = uid
        this.name = name
        this.address = address
        this.email = email
        this.phone = phoneNumber
        this.totalPrice = totalPrice
        this.time = time
        this.foodNames = foodNames
        this.foodImages = foodImages
        this.foodIngredients = foodIngredients
        this.foodDescription = foodDescription
        this.foodPrices = foodPrices
        this.isOrderAccepted = orderAccepted
        this.isPaymentReceived = paymentReceived

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(totalPrice)
        parcel.writeString(time)
        parcel.writeByte(if (isOrderAccepted) 1 else 0)
        parcel.writeByte(if (isPaymentReceived) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderPlaceDetailsModel> {
        override fun createFromParcel(parcel: Parcel): OrderPlaceDetailsModel {
            return OrderPlaceDetailsModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderPlaceDetailsModel?> {
            return arrayOfNulls(size)
        }
    }


}