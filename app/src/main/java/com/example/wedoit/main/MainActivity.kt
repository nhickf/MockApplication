package com.example.wedoit.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wedoit.*
import com.example.wedoit.repository.DatabaseRepository
import com.example.wedoit.repository.FakeData
import com.example.wedoit.repository.entities.LocationEntities
import com.example.wedoit.transaction.TransactionActivity
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
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
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() , OnMapReadyCallback  ,
    IGlobalCallbacks.onCardViewClick,
    IGlobalCallbacks.OnInquiryClick, MapboxMap.OnMapClickListener{


    private lateinit var  location : LocationEntities

    private lateinit var locationEngine : LocationEngine

    private lateinit var mapbox: MapboxMap

    private lateinit var permissionManager : PermissionsManager

    private lateinit var featureCollection: FeatureCollection

    private val databaseRepository = DatabaseRepository()

    private lateinit var loadedMapStyle: Style


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Giving instance to the Mapbox before anything else.
        Mapbox.getInstance(this,"pk.eyJ1IjoibmhpY2tmbG9yZXMiLCJhIjoiY2swc3hxaDhtMDdsdzNscXM2Y21sb21xOSJ9.Ag3igsM6IUWUOXUJh8q9EA")
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        location = LocationEntities(14.6423 ,121.008 )

        //Giving savedInstance
        map_container.onCreate(savedInstanceState)

        //LocationEngine will be use for location request.
        locationEngine  = LocationEngineProvider.getBestLocationEngine(this)

        //Syncing the MapView
        map_container.getMapAsync(this)

        floating_button.setOnClickListener {
            //Getting location updates
            getLocationUpdates()
            mapbox.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionBuilder(location,18.0)),5000)

            initializeFeatureCollection()

            updateStoreMarkers()
        }

    }
    override fun onMapClick(point: LatLng): Boolean {
        val screenPoint = mapbox.projection.toScreenLocation(point)
        val features = mapbox.queryRenderedFeatures(screenPoint,"layers")

        if (features.isNotEmpty()){
            val selectedFeature = features[0]

            val storeFragment = StoreDetailsFragment(
                selectedFeature.getStringProperty("Name"),
                this
            )
            storeFragment.show(supportFragmentManager,"StoreFragment")
            storeFragment.isCancelable = true

        }

        return false
    }

    private fun updateStoreMarkers() : GeoJsonSource{
        val geoSource : GeoJsonSource? = mapbox.style?.getSourceAs("stores")
        geoSource?.setGeoJson(featureCollection)
        return geoSource!!
    }

    private fun loadStores(){
        val adapter = StoreRecyclerViewAdapter(this, this)

        recycler_view_main.adapter = adapter
        recycler_view_main.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        adapter.setStoreList(FakeData.mockListOfStores)
    }

    //Callback when the mapView is already loaded
    override fun onMapReady(mapboxMap: MapboxMap) {

        this.mapbox = mapboxMap

        mapboxMap.addOnMapClickListener(this)
        //Setting for what kind of style of map
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {

            loadedMapStyle = it

            // Map is set up and the style has loaded. Now you can add data or make other map adjustments
            //Adding location permission.
           if (locationPermission()) {

               enableLocationComponents(it)

               getLocationUpdates()

               initializeFeatureCollection()

               initializeMarkers(it)

               loadStores()

               mapbox.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionBuilder(location, 18.0)), 5000)

           }

        }

    }

    private fun initializeFeatureCollection(){
        featureCollection = FeatureCollection.fromFeatures(ArrayList<Feature>())

        val storeListOfCoordinate = ArrayList<Feature>()

        for (coordinates in databaseRepository.getListOfStores){

            val feature = Feature.fromGeometry(Point.fromLngLat(location.longitude+ coordinates.longitude,location.latitude+ coordinates.latitude))

            feature.addStringProperty("Name",coordinates.storeName)

            storeListOfCoordinate.add(feature)
        }

        featureCollection = FeatureCollection.fromFeatures(storeListOfCoordinate)

    }
    private fun initializeMarkers(loadedMapStyle: Style){

        loadedMapStyle.addImage("markers",ResourcesCompat.getDrawable(resources,
            R.drawable.ic_place_marker_24dp,null)!!)
        loadedMapStyle.addSource( GeoJsonSource("stores", featureCollection))
        loadedMapStyle.addLayer( SymbolLayer("layers", "stores").withProperties(
            iconImage("markers"),
            iconAllowOverlap(true),
            iconOffset( arrayOf(0f, -4f)),
            iconSize(1.5f)))
    }

    override fun onStoreCardClick(location: LocationEntities) {
        updateCameraPosition(location)
    }

    //Requesting for location permission
    private fun locationPermission():Boolean{

        if (PermissionsManager.areLocationPermissionsGranted(this)){
            getLocationUpdates()
            return true
        }else {
            permissionManager = PermissionsManager(permissionsListener)
            permissionManager.requestLocationPermissions(this)
        }
        return false
    }

    //Permission listener
    private var permissionsListener: PermissionsListener = object : PermissionsListener {

        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            Log.d("onExplanationNeeded","Need")
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {

                // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
                mapbox.getStyle {

                    enableLocationComponents(it)

                    initializeFeatureCollection()

                    initializeMarkers(it)

                    getLocationUpdates()

                    loadStores()

                }

            } else {

                // User denied the permission
                finish()

            }
        }
    }

    //For the camera Animation.
    private fun cameraPositionBuilder(newlocation : LocationEntities , zoom:Double): CameraPosition{
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

    private fun updateCameraPosition(location: LocationEntities){
        mapbox.easeCamera(CameraUpdateFactory.newCameraPosition(cameraPositionBuilder(location,20.0)))
    }

    //RequestPermission callback.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    //For marking the current location
    private fun enableLocationComponents(loadedMapStyle:Style){

        val locationComponent = mapbox.locationComponent

        locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this,loadedMapStyle).build())

        locationComponent.isLocationComponentEnabled = true

        locationComponent.cameraMode = CameraMode.TRACKING

        locationComponent.renderMode = RenderMode.COMPASS


    }

    //Responsible for location updates
    private fun getLocationUpdates() : LocationEngineRequest{

        val request = LocationEngineRequest.Builder(10000L)
            .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
            .setMaxWaitTime(10000L *15)
            .build()

        locationEngine.requestLocationUpdates(request,getLastLocationCallback(), Looper.getMainLooper())
        locationEngine.getLastLocation(getLastLocationCallback())

        return request
    }

    //Callback for getting the last location.
    private fun getLastLocationCallback() = object : LocationEngineCallback<LocationEngineResult>{

        override fun onSuccess(result: LocationEngineResult?) {

            location = (LocationEntities(result?.lastLocation!!.latitude,result.lastLocation!!.longitude))

        }

        override fun onFailure(exception: Exception) {
            Toast.makeText(this@MainActivity,exception.localizedMessage,Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()
        map_container.onStart()
    }

    override fun onResume() {
        super.onResume()
        map_container.onResume()

    }

    override fun onStop() {
        super.onStop()
        map_container.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_container.onDestroy()
        locationEngine.removeLocationUpdates(getLastLocationCallback())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_container.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_container.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }

    override fun onButtonClick() {
        val intent = Intent(this, TransactionActivity::class.java)
        startActivity(intent)
        finish()
    }
}
