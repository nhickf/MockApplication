package com.example.wedoit.transaction.delivery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentDeliveryManBinding
import com.example.wedoit.transaction.item.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeliveryManBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentDeliveryManBinding
    private lateinit var itemViewModel: ItemViewModel

    companion object{
        fun newInstance(itemViewModel: ItemViewModel):DeliveryManBottomFragment{
            val deliveryManBottomFragment = DeliveryManBottomFragment()
            deliveryManBottomFragment.itemViewModel = itemViewModel
            return deliveryManBottomFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity.let {
            itemViewModel = ViewModelProviders.of(it!!).get(ItemViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_delivery_man,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deliveryManFragment = this
        binding.executePendingBindings()

    }

    fun acceptDeliveryMan(){
        itemViewModel.setDeliveryManStatus(1)
        dismiss()
    }

    fun cancelDeliveryMan(){
        itemViewModel.setDeliveryManStatus(2)
        dismiss()
    }
}