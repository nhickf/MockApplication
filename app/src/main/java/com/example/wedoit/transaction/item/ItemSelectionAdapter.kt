package com.example.wedoit.transaction.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wedoit.R
import com.example.wedoit.databinding.RecyclerViewItemTrasactionLayoutBinding
import com.example.wedoit.repository.entities.ItemEntities

class ItemSelectionAdapter(private val context: Context , private val itemViewModel: ItemViewModel) : RecyclerView.Adapter<ItemSelectionAdapter.ViewHolder>() {

    private lateinit var binding: RecyclerViewItemTrasactionLayoutBinding
    private var itemArrayList = ArrayList<ItemEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.recycler_view_item_trasaction_layout,parent,false)
        return ViewHolder(
            binding,
            itemViewModel
        )
    }

    override fun getItemCount(): Int {
        return itemArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(itemArrayList[position])
    }

    class ViewHolder(private val binding: RecyclerViewItemTrasactionLayoutBinding , private val itemViewModel: ItemViewModel) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : ItemEntities){
            binding.item = item
            binding.itemViewModel = itemViewModel
            binding.executePendingBindings()
        }
    }

    fun setItemArrayList( items : ArrayList<ItemEntities>){
        itemArrayList = items
        notifyDataSetChanged()
    }
}