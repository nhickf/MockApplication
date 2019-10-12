package com.example.wedoit.repository.entities

data class ItemEntities (
    //Constructors
    val itemCode:Int,
    val itemDescription:String,
    val itemType:Int,
    val itemAmount:Double,
    val itemQuantity:Int)
{
    //SecondaryValues
    val itemTransactionNumber = ""
    val itemInstruction:String = ""
    val cutomizedItemAmount = "P$itemAmount"
}