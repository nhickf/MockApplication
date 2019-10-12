package com.example.wedoit.transaction.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.wedoit.repository.DatabaseRepository
import com.example.wedoit.repository.entities.ItemEntities

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseRepository = DatabaseRepository()

    private var itemList = MutableLiveData<ArrayList<ItemEntities>>()
    private var selectedItem = MutableLiveData<ItemEntities>()
    private var selectedItemsList = MutableLiveData<ArrayList<ItemEntities>>()
    private var confirmedItemsList = MutableLiveData<ArrayList<ItemEntities>>()
    private var checkOutItemsList = MutableLiveData<ArrayList<ItemEntities>>()
    private var deliveryManStatus = MutableLiveData<Int>()
    private var transactionStatus = MutableLiveData<Int>()


    fun setItemsList(headerName : String){
        when (headerName){
            "Services" -> itemList.postValue(databaseRepository.getListOfServicesItems)
            "Detergents" -> itemList.postValue( databaseRepository.getListOfDetergentItems)
            "Fabric Conditioner" -> itemList.postValue(databaseRepository.getListOfFabricConditionerItems)
            else->{
                itemList.postValue(databaseRepository.getListOfServicesItems)
            }
        }
    }

    fun getListOfItems(): MutableLiveData<ArrayList<ItemEntities>>{
        return itemList
    }

    fun getSelectedItems(): MutableLiveData<ItemEntities>{
        return selectedItem
    }

    fun addSelectedItems(itemEntities: ItemEntities){
        selectedItem.postValue(itemEntities)
    }

    fun setSelectedItemList(list : ArrayList<ItemEntities>){
        selectedItemsList.postValue(list)
    }

    fun getSelectedItemList() : MutableLiveData<ArrayList<ItemEntities>>{
        return selectedItemsList
    }

    fun setConfirmedItemList(list : ArrayList<ItemEntities>){
        confirmedItemsList.postValue(list)
    }

    fun getConfirmedItemList() : MutableLiveData<ArrayList<ItemEntities>>{
        return confirmedItemsList
    }

    fun setDeliveryManStatus(status : Int){
        deliveryManStatus.postValue(status)
    }

    fun getDeliveryManStatus():MutableLiveData<Int>{
        return deliveryManStatus
    }

    fun setCheckOutList(list : ArrayList<ItemEntities>){
        checkOutItemsList.postValue(list)
    }

    fun getCheckOutList(): MutableLiveData<ArrayList<ItemEntities>>{
        return checkOutItemsList
    }

    fun setTransactionStatus(status:Int){
        transactionStatus.postValue(status)
    }

    fun getTransactionStatus():MutableLiveData<Int>{
        return transactionStatus
    }
}