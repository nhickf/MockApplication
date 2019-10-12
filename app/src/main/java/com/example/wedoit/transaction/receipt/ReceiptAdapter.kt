package com.example.wedoit.transaction.receipt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wedoit.R
import com.example.wedoit.databinding.RecyclerViewShowCartLayoutBinding
import com.example.wedoit.repository.entities.ItemEntities

class ReceiptAdapter(private val context: Context) : RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {

    private lateinit var confirmedItemsBinding : RecyclerViewShowCartLayoutBinding
    private var confirmedList = ArrayList<ItemEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        confirmedItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recycler_view_show_cart_layout,parent,false)
        return ViewHolder(confirmedItemsBinding)
    }

    override fun getItemCount(): Int {
        return confirmedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(confirmedList[position])
    }

    class ViewHolder(private val binding: RecyclerViewShowCartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:ItemEntities){
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun setItemList(itemList : ArrayList<ItemEntities>){
        confirmedList = itemList
        notifyDataSetChanged()
    }
}