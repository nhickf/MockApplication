package com.example.wedoit.transaction.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentSearchDeliveryManBinding

class SearchDeliveryManFragment : Fragment() {

    private lateinit var binding : FragmentSearchDeliveryManBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_search_delivery_man,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}