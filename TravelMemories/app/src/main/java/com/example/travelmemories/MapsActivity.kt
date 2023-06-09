package com.example.travelmemories

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.travelmemories.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{//, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: LatLng
    private lateinit var selectedLocation: Address
    private lateinit var sharedPref:SharedPreferences
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.select_location)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val showMapScaleSelection = sharedPref.getBoolean("showMapSelection", true)
        map.uiSettings.isZoomControlsEnabled = showMapScaleSelection
        Log.d("ShowScale", showMapScaleSelection.toString())

        val mapOptions = resources.getStringArray(R.array.types_of_app_map_mode)
        val mapModeSelection = sharedPref.getString("preferedMapMode", "sss").toString()
        if(mapModeSelection == mapOptions[0]){
            map.mapType=GoogleMap.MAP_TYPE_SATELLITE
        }
        if(mapModeSelection == mapOptions[1]){
            map.mapType=GoogleMap.MAP_TYPE_NORMAL
        }


        setUpMap()
        map.setOnMapClickListener { latlng ->
            val location = LatLng(latlng.latitude, latlng.longitude)
            this.lastLocation = location
            map.clear()
            var marker = MarkerOptions().position(location)
            // this.marker=marker
            val geocoder = Geocoder(this, Locale.getDefault())
            var addresses: List<Address>?=null
            try {
                addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude,1)
                marker.title(addresses!![0].getAddressLine(0))
                this.selectedLocation = addresses!![0]
                Log.d("current loc",addresses!![0].getAddressLine(0) )
            }
            catch (e: IOException){
            }

            map.addMarker(marker)

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5f))
        }
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("Current action", "in onCreateOptionsMenu")

        menuInflater.inflate(R.menu.memory_add, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra("operation","back")
                setResult(RESULT_OK, intent);
                finish()
                return true
            }
            R.id.save_activity_button -> {
                val intent = Intent()
                intent.putExtra("operation","save")
                intent.putExtra("address",this.selectedLocation )
                intent.putExtra("long",this.lastLocation.longitude)
                intent.putExtra("lat",this.lastLocation.latitude)
                Log.d("Save location",this.selectedLocation.getAddressLine(0) )
                setResult(RESULT_OK, intent);
                finish()
                return true
            }

        }
        return true
    }
}

