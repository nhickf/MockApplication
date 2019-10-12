package com.example.wedoit.transaction.item

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentItemSelectionBinding
import com.example.wedoit.repository.DatabaseRepository
import com.example.wedoit.repository.entities.ItemEntities
import com.google.android.material.tabs.TabLayout

class ItemSelectionFragment : Fragment() , TabLayout.OnTabSelectedListener {


    private lateinit var itemSelectionBinding: FragmentItemSelectionBinding
    private lateinit var itemViewModel: ItemViewModel
    private val dataRepository = DatabaseRepository()
    private val selectedItems = ArrayList<ItemEntities>()

    companion object{
        fun newInstance(itemViewModel: ItemViewModel) : ItemSelectionFragment {
            val itemSelectionFragment = ItemSelectionFragment()
            itemSelectionFragment.itemViewModel = itemViewModel
            return itemSelectionFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity.let {
            itemViewModel = ViewModelProviders.of(it!!).get(ItemViewModel::class.java)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        itemSelectionBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
            R.layout.fragment_item_selection,container,false)

        return itemSelectionBinding.root
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()

        val adapter = ItemSelectionAdapter(
            requireContext(),
            itemViewModel
        )

        addTableLayoutTab()

        itemSelectionBinding.recyclerViewItems.adapter = adapter
        itemSelectionBinding.recyclerViewItems.layoutManager = LinearLayoutManager(requireContext())

        itemViewModel.getListOfItems().observe(this, Observer {
            adapter.setItemArrayList(it)
        })

        itemViewModel.getSelectedItems().observe(this, Observer {
            selectedItems.add(it)
        })

    }

    private fun bind(){
        itemSelectionBinding.selectedItems = selectedItems
        itemSelectionBinding.viewModel = itemViewModel
        itemSelectionBinding.executePendingBindings()
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        itemViewModel.setItemsList(p0?.text.toString())
    }

    private fun addTableLayoutTab(){

        for ( header in dataRepository.getListHeaders){

            val tab = itemSelectionBinding.tabLayoutHeaders.newTab()
            tab.text = header.headerDescription
            itemSelectionBinding.tabLayoutHeaders.addTab(tab)
        }

        itemSelectionBinding.tabLayoutHeaders.addOnTabSelectedListener(this)
    }


}