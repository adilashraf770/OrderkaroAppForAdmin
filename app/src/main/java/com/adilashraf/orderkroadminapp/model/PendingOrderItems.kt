package com.adilashraf.orderkroadminapp.model



class PendingOrderItems {
    var foodImage: Int = 0
    var foodName: String = ""
    var price: Int = 0

    constructor()
    constructor(fImage: Int, fName: String , price: Int)   {
        this.foodImage = fImage
        this.foodName = fName
        this.price = price

    }


}