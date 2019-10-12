package com.example.wedoit.repository

import com.example.wedoit.IDatabaseRepository
import com.example.wedoit.repository.entities.HeaderEntities
import com.example.wedoit.repository.entities.HistoryEntities
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.repository.entities.StoreEntities

class DatabaseRepository : IDatabaseRepository {


    override fun getSelectedStore(storeName: String): StoreEntities? {
        return FakeData.mockStoreEntities(storeName)
    }

    override val getListHeaders: ArrayList<HeaderEntities>
        get() = FakeData.mockListOfHeaderServices

    override val getListOfItems: ArrayList<ItemEntities>
        get() = FakeData.mockListOfItems

    override val getListOfHistory: ArrayList<HistoryEntities>
        get() = FakeData.mockListOfHistory

    override val getListOfAds: ArrayList<String>
        get() = FakeData.mockListOfAds

    override val getListOfStores: ArrayList<StoreEntities>
        get() = FakeData.mockListOfStores

    override val getListOfServicesItems: ArrayList<ItemEntities>
        get() = FakeData.mockListOfServiceItems

    override val getListOfDetergentItems: ArrayList<ItemEntities>
        get() = FakeData.mockListOfDetergentItems

    override val getListOfFabricConditionerItems: ArrayList<ItemEntities>
        get() = FakeData.mockListOfFabricConditionerItems
}