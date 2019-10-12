package com.example.wedoit.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.wedoit.IGlobalCallbacks
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentStoreDetailsBinding
import com.example.wedoit.repository.DatabaseRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StoreDetailsFragment(private val storeName : String ,private var onCallback : IGlobalCallbacks.OnInquiryClick) : BottomSheetDialogFragment() {

    private lateinit var viewBinding : FragmentStoreDetailsBinding
    private lateinit var databaseRepository: DatabaseRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCallback = activity as IGlobalCallbacks.OnInquiryClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding =  DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.fragment_store_details,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseRepository = DatabaseRepository()

        viewBinding.store = databaseRepository.getSelectedStore(storeName)
        viewBinding.executePendingBindings()

        Glide.with(requireContext())
            .load(viewBinding.store?.imageUrl)
            .centerCrop()
            .into(viewBinding.imageViewStoreImage)

        viewBinding.buttonInquiry.setOnClickListener {
            onCallback.onButtonClick()
        }

    }
}