package com.adilashraf.orderkroadminapp.model

class OrderDispatchItems {

    var customerName: String = ""
    var status: String = ""

    constructor()

    constructor(cName: String, s: String){
        this.customerName = cName
        this.status = s
    }

}