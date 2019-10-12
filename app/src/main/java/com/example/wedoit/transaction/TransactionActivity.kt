package com.example.wedoit.transaction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wedoit.transaction.item.ItemSelectionFragment
import com.example.wedoit.transaction.item.ItemViewModel
import com.example.wedoit.R
import com.example.wedoit.transaction.cart.ShowCartFragment
import com.example.wedoit.transaction.delivery.DeliveryFragment
import com.example.wedoit.transaction.delivery.DeliveryManBottomFragment
import com.example.wedoit.transaction.receipt.ReceiptFragment

class TransactionActivity : AppCompatActivity(){

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        displayFragment(ItemSelectionFragment.newInstance(itemViewModel))

        itemViewModel.getSelectedItemList().observe(this, Observer {

            val showCartFragment = ShowCartFragment().newInstance(it,itemViewModel)
            showCartFragment.show(supportFragmentManager,"Cart")
            showCartFragment.isCancelable = true

        })

        itemViewModel.getConfirmedItemList().observe(this, Observer {

            displayFragment(ReceiptFragment.newInstance(it,itemViewModel))

        })

        itemViewModel.getCheckOutList().observe(this, Observer {

            displayFragment(DeliveryFragment.newInstance(it,itemViewModel))

        })

        itemViewModel.getDeliveryManStatus().observe(this, Observer {

            if  (it == 0) {
                val showDeliveryMan = DeliveryManBottomFragment.newInstance(itemViewModel)
                showDeliveryMan.show(supportFragmentManager, "Man")
                showDeliveryMan.isCancelable = false
            }

        })

        itemViewModel.getTransactionStatus().observe(this, Observer {
            finish()
        })
    }

    private fun displayFragment(fragment : Fragment){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_transaction_container,fragment)
            .commit()

    }
}