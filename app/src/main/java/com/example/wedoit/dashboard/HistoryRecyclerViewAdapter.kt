package com.example.wedoit.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wedoit.R
import com.example.wedoit.databinding.RecyclerViewHistoryLayoutBinding
import com.example.wedoit.repository.entities.HistoryEntities

class HistoryRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    private var historyList = ArrayList<HistoryEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.recycler_view_history_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return  historyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    class ViewHolder(private val historyLayoutBinding: RecyclerViewHistoryLayoutBinding) : RecyclerView.ViewHolder(historyLayoutBinding.root) {

        fun bind(historyEntities: HistoryEntities){
            historyLayoutBinding.history = historyEntities
            historyLayoutBinding.executePendingBindings()
        }
    }

    fun setHistory(arrayList:ArrayList<HistoryEntities>){
        historyList = arrayList
        notifyDataSetChanged()
    }
}