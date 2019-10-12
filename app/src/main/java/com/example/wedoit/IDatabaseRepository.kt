package com.example.wedoit

import com.example.wedoit.repository.entities.HeaderEntities
import com.example.wedoit.repository.entities.HistoryEntities
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.repository.entities.StoreEntities

interface IDatabaseRepository {

    val getListOfStores : ArrayList<StoreEntities>

    val getListOfAds : ArrayList<String>

    val getListOfHistory : ArrayList<HistoryEntities>

    fun getSelectedStore(storeName:String) : StoreEntities?

    val getListHeaders : ArrayList<HeaderEntities>

    val getListOfItems : ArrayList<ItemEntities>

    val getListOfServicesItems : ArrayList<ItemEntities>
    val getListOfDetergentItems : ArrayList<ItemEntities>
    val getListOfFabricConditionerItems : ArrayList<ItemEntities>

}