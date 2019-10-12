package com.example.wedoit.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wedoit.R
import com.example.wedoit.databinding.RecyclerViewAdsLayoutBinding

class AdsRecyclerViewAdapter(private val context:Context) : RecyclerView.Adapter<AdsRecyclerViewAdapter.ViewHolder>() {

    private var adsList = ArrayList<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.recycler_view_ads_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(adsList[position])
            .centerCrop()
            .into(holder.adsLayoutBinding.imageViewAds)

    }

    class ViewHolder(val adsLayoutBinding: RecyclerViewAdsLayoutBinding) : RecyclerView.ViewHolder(adsLayoutBinding.root)

    fun setAdsList(list :ArrayList<String>){
        adsList = list
        notifyDataSetChanged()
     }
}