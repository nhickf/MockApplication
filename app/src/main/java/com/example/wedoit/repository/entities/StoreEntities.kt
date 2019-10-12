package com.example.wedoit.repository.entities

data class StoreEntities (
    val storeName : String,
    val storeAddress : String,
    val latitude : Double,
    val longitude : Double,
    val imageUrl : String,
    val storeRate : Double
){
    val cutomizerRate = "P$storeRate Per Kilo"
}

