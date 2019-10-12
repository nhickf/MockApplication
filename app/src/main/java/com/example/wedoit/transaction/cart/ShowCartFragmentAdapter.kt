package com.example.wedoit.transaction.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wedoit.R
import com.example.wedoit.databinding.RecyclerViewShowCartLayoutBinding
import com.example.wedoit.repository.entities.ItemEntities

class ShowCartFragmentAdapter(private val context: Context) : RecyclerView.Adapter<ShowCartFragmentAdapter.ViewHolder>() {

    private var arrayListSelectedItems = ArrayList<ItemEntities>()
    private lateinit var selectedItemsBinding : RecyclerViewShowCartLayoutBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        selectedItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.recycler_view_show_cart_layout,parent,false)
        return ViewHolder(
            selectedItemsBinding
        )
    }

    override fun getItemCount(): Int {
        return arrayListSelectedItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayListSelectedItems[position]
        holder.bind(item)
    }

    class ViewHolder(private val binding : RecyclerViewShowCartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(selectedItems : ItemEntities){
            binding.item = selectedItems
            binding.executePendingBindings()
        }
    }

    fun setArrayList(arrayList : ArrayList<ItemEntities>){
        arrayListSelectedItems = arrayList
        notifyDataSetChanged()
    }
}