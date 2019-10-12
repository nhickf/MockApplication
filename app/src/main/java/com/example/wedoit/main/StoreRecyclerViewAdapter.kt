package com.example.wedoit.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wedoit.IGlobalCallbacks
import com.example.wedoit.databinding.RecyclerViewMainLayoutBinding
import com.example.wedoit.repository.entities.StoreEntities
import com.example.wedoit.repository.entities.LocationEntities

class StoreRecyclerViewAdapter(private val context: Context , private val callbacks: IGlobalCallbacks.onCardViewClick) : RecyclerView.Adapter<StoreRecyclerViewAdapter.ViewAdapter>() {

    private var storeList = ArrayList<StoreEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter {
        return ViewAdapter(RecyclerViewMainLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
       return storeList.size
    }

    override fun onBindViewHolder(holder: ViewAdapter, position: Int) {
        val store = storeList[position]

        Glide.with(context)
            .load(store.imageUrl)
            .into(holder.dataBinder.imageViewStore)

        holder.dataBinder.storeContainer.setOnClickListener { callbacks.onStoreCardClick(
            LocationEntities(store.latitude,store.longitude)) }

        holder.bind(store)

    }

    inner class ViewAdapter(val dataBinder: RecyclerViewMainLayoutBinding) : RecyclerView.ViewHolder(dataBinder.root) {



        fun bind(store: StoreEntities){
            dataBinder.storeEntities = store
            dataBinder.executePendingBindings()
        }
    }

    fun setStoreList(storeList: ArrayList<StoreEntities>){
        this.storeList = storeList
        notifyDataSetChanged()
    }
}