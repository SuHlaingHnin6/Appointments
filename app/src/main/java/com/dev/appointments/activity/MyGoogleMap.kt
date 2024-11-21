package com.dev.appointments.activity

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.dev.appointments.R


class MyGoogleMap : AppCompatActivity() ,OnMapReadyCallback{
    private lateinit var myMap:GoogleMap

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var mlat : String = ""
    var mlng :String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_google_map)

        val mapView = findViewById<MapView>(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        myMap = googleMap
        val zoomLevel: Float = 13.0f
//
        myMap.moveCamera(CameraUpdateFactory.zoomIn())
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
            myMap.isMyLocationEnabled = true
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location: Location? ->
                // Handle the location object here
                if (location != null) {
                    mlat = location.latitude.toString()
                    mlng = location.longitude.toString()
                    Log.e("currentlocation","currentlocation : $mlat , $mlng")

                    val currentLocation = LatLng(mlat.toDouble(),mlng.toDouble())
                    Log.e("currentlocation","currentlocation$$ : $currentLocation")
                    Log.e("currentlocation","currentlocation$$ : $mlat , $mlng")
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13f))
//                    myMap.addMarker(MarkerOptions().position(currentLocation).title("$mlat,$mlng").snippet("I' here!!").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_ic)))

                    myMap.setOnMapClickListener { latLng ->
                        // latLng contains the latitude and longitude of the clicked point
                        val latitude = latLng.latitude
                        val longitude = latLng.longitude

                        // Log or display the coordinates
                        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val coordinates = "$latitude,$longitude"
                        Log.d("MapClick", "Latitude: $latitude, Longitude: $longitude")
                        val clip = android.content.ClipData.newPlainText("Coordinates", coordinates)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(this, "Coordinates copied: $coordinates", Toast.LENGTH_SHORT).show()
                        // Optionally, add a marker at the clicked location
                        myMap.addMarker(MarkerOptions().position(latLng))
                        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }

                } else {
                    Log.d("MainActivity", "No location available")
                }
            }

        }


    }

    override fun onResume() {
        super.onResume()
        findViewById<MapView>(R.id.map).onResume()
    }

    override fun onPause() {
        super.onPause()
        findViewById<MapView>(R.id.map).onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        findViewById<MapView>(R.id.map).onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        findViewById<MapView>(R.id.map).onLowMemory()
    }

}