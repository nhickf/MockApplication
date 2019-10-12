package com.example.wedoit.transaction.delivery

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wedoit.R
import com.example.wedoit.databinding.FragmentDeliveryBinding
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.repository.entities.LocationEntities
import com.example.wedoit.transaction.item.ItemViewModel
import com.mapbox.android.core.location.*
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import java.lang.Exception

class DeliveryFragment : Fragment() , OnMapReadyCallback , LocationEngineCallback<LocationEngineResult> {

    private lateinit var binding : FragmentDeliveryBinding
    private lateinit var mapBox : MapboxMap
    private lateinit var locationEngine : LocationEngine
    private lateinit var location : LocationEntities
    private lateinit var itemViewModel : ItemViewModel
    private lateinit var confirmedArrayList: ArrayList<ItemEntities>

    companion object{
        fun newInstance(confirmedArrayList: ArrayList<ItemEntities>, itemViewModel: ItemViewModel):DeliveryFragment{
            val deliveryManFragment = DeliveryFragment()
            deliveryManFragment.itemViewModel = itemViewModel
            deliveryManFragment.confirmedArrayList = confirmedArrayList
            return deliveryManFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity.let {
            itemViewModel = ViewModelProviders.of(it!!).get(ItemViewModel::class.java)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Mapbox.getInstance(requireContext(),"pk.eyJ1IjoibmhpY2tmbG9yZXMiLCJhIjoiY2swc3hxaDhtMDdsdzNscXM2Y21sb21xOSJ9.Ag3igsM6IUWUOXUJh8q9EA")

        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_delivery,container,false)

        binding.mapViewContainer.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationEngine  = LocationEngineProvider.getBestLocationEngine(requireContext())


        binding.mapViewContainer.getMapAsync(this)

        itemViewModel.getDeliveryManStatus().observe(this, Observer {
            when (it){
                1 -> itemViewModel.setConfirmedItemList(confirmedArrayList)
                2 -> gettingDeliveryMan()
            }
        })

    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapBox = mapboxMap

        mapBox.setStyle(Style.MAPBOX_STREETS){
            getLocationUpdates()
            enableLocationComponents(it)

        }

        gettingDeliveryMan()

    }

    //Responsible for location updates
    private fun getLocationUpdates() : LocationEngineRequest {

        val request = LocationEngineRequest.Builder(10000L)
            .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
            .setMaxWaitTime(10000L *15)
            .build()

        locationEngine.requestLocationUpdates(request,this, Looper.getMainLooper())
        locationEngine.getLastLocation(this)

        return request
    }

    //For marking the current location
    private fun enableLocationComponents(loadedMapStyle:Style){

        val locationComponent = mapBox.locationComponent

        locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(requireContext(),loadedMapStyle).build())

        locationComponent.isLocationComponentEnabled = true

        locationComponent.cameraMode = CameraMode.TRACKING

        locationComponent.renderMode = RenderMode.COMPASS

    }

    override fun onSuccess(result: LocationEngineResult?) {
        location = LocationEntities(result?.lastLocation!!.latitude, result.lastLocation!!.longitude)

        mapBox.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionBuilder(location,18.0)),5000)

    }

    override fun onFailure(exception: Exception) {

    }

    private fun gettingDeliveryMan(){

        binding.fragmentSearchingDeliveryManContainer.visibility = View.VISIBLE

        childFragmentManager.beginTransaction().apply {
            add(R.id.fragment_searching_delivery_man_container,SearchDeliveryManFragment(),"loading")
            commit()
        }

        val handler = Handler()
        handler.postDelayed({
            binding.fragmentSearchingDeliveryManContainer.visibility = View.GONE

            itemViewModel.setDeliveryManStatus(0)

        }, 10000)
    }

    //For the camera Animation.
    private fun cameraPositionBuilder(newlocation : LocationEntities , zoom:Double): CameraPosition {
        var latitude = location.latitude
        var longitude = location.longitude
        if (zoom == 20.0 ){
            latitude += newlocation.latitude
            longitude += newlocation.longitude
        }

        return CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(zoom)
            .bearing(180.0)
            .tilt(20.0)
            .build()
    }

    override fun onStart() {
        super.onStart()
        binding.mapViewContainer.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapViewContainer.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.mapViewContainer.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewContainer.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapViewContainer.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapViewContainer.onSaveInstanceState(outState)
    }
}