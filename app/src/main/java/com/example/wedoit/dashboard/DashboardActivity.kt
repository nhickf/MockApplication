package com.example.wedoit.dashboard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.wedoit.main.MainActivity
import com.example.wedoit.R
import com.example.wedoit.databinding.ActivityDashboardBinding
import com.example.wedoit.repository.DatabaseRepository

class DashboardActivity : AppCompatActivity() {


    private lateinit var dashboardBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val databaseRepository = DatabaseRepository()

        val adsAdapter = AdsRecyclerViewAdapter(this)
        val historyAdapter = HistoryRecyclerViewAdapter(this)

        dashboardBinding.dashBoardActivity = this
        dashboardBinding.executePendingBindings()

        dashboardBinding.recyclerViewAdsDashboard.adapter = adsAdapter
        dashboardBinding.recyclerViewAdsDashboard.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        dashboardBinding.recyclerViewHistoryDashboard.adapter = historyAdapter
        dashboardBinding.recyclerViewHistoryDashboard.layoutManager = LinearLayoutManager(this)

        historyAdapter.setHistory(databaseRepository.getListOfHistory)
        adsAdapter.setAdsList(databaseRepository.getListOfAds)

    }

    fun getToMapActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}