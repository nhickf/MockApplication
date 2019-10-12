package com.example.wedoit.transaction.receipt

import android.content.Context
import android.database.DatabaseUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentReceiptBinding
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.transaction.item.ItemViewModel

class ReceiptFragment : Fragment() {

    private lateinit var binding : FragmentReceiptBinding
    private lateinit var itemViewModel : ItemViewModel
    private lateinit var confirmedArrayList: ArrayList<ItemEntities>

    companion object{
        fun newInstance(confirmedArrayList: ArrayList<ItemEntities>,itemViewModel: ItemViewModel) : ReceiptFragment {
            val receiptFragment = ReceiptFragment()
            receiptFragment.confirmedArrayList= confirmedArrayList
            receiptFragment.itemViewModel = itemViewModel
            return receiptFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity.let {
            itemViewModel = ViewModelProviders.of(it!!).get(ItemViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_receipt,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ReceiptAdapter(requireContext())

        binding.recyclerViewOrderSummaryContainer.adapter = adapter
        binding.recyclerViewOrderSummaryContainer.layoutManager = LinearLayoutManager(requireContext())


        confirmedArrayList.add(ItemEntities(100,"Delivery Fee",10,10.0,1))
        adapter.setItemList(confirmedArrayList)
        getTotalAmount()

        binding.buttonFindRider.setOnClickListener {
            itemViewModel.setTransactionStatus(1)
        }
    }


    private fun getTotalAmount(){

        var totalAmount = 0.00

        for (item in confirmedArrayList){
            totalAmount += item.itemAmount
        }

        binding.textViewTotalAmountValue.text = "P$totalAmount"
    }
}