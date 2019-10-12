package com.example.wedoit.transaction.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentShowCartLayoutBinding
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.transaction.item.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowCartFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentShowCartLayoutBinding
    private lateinit var arrayListSelectedItems : ArrayList<ItemEntities>
    private lateinit var itemViewModel: ItemViewModel

    fun newInstance(arrayList:ArrayList<ItemEntities> , itemViewModel: ItemViewModel ): ShowCartFragment {
        val showCartFragment = ShowCartFragment()
        showCartFragment.setArraySelectedItems(arrayList)
        showCartFragment.itemViewModel = itemViewModel
        return showCartFragment
    }

    private fun setArraySelectedItems(arrayList: ArrayList<ItemEntities>){
        this.arrayListSelectedItems = arrayList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
            R.layout.fragment_show_cart_layout,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShowCartFragmentAdapter(requireContext())

        binding.recyclerViewOrderItems.adapter = adapter
        binding.recyclerViewOrderItems.layoutManager = LinearLayoutManager(requireContext())

        adapter.setArrayList(arrayListSelectedItems)

        binding.buttonCheckOut.setOnClickListener {
            itemViewModel.setCheckOutList(arrayListSelectedItems)
            dismiss()
        }

        getTotalAmount()
    }

    private fun getTotalAmount(){

        var totalAmount = 0.00

        for (item in arrayListSelectedItems){
            totalAmount += item.itemAmount
        }

        binding.textViewTotalAmountValue.text = "P$totalAmount"
    }
}