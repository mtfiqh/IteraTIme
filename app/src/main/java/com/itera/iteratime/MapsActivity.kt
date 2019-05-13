package com.itera.iteratime

import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var mapFragment : SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val itera = LatLng(-5.358293, 105.314823)
        mMap.addMarker(MarkerOptions().position(itera).title("Institut Teknologi Sumatera"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itera))

        val gda = LatLng(-5.357919,105.314414)
        mMap.addMarker(MarkerOptions().position(gda).title("Gedung A - Rektorat ITERA"))

        val gdb = LatLng(-5.357966,105.315304)
        mMap.addMarker(MarkerOptions().position(gdb).title("Gedung B - Rektorat ITERA"))

        val gdc = LatLng(-5.358449,105.313568)
        mMap.addMarker(MarkerOptions().position(gdc).title("Gedung C - Gedung Perkuliahan ITERA"))

        val gdd = LatLng(-5.358800,105.313438)
        mMap.addMarker(MarkerOptions().position(gdd).title("Gedung D - Gedung Perkuliahan ITERA"))

        val gde = LatLng(-5.360101,105.315462)
        mMap.addMarker(MarkerOptions().position(gde).title("Gedung E - Gedung Perkuliahan ITERA"))

        val gdgku = LatLng(-5.360876,105.310283)
        mMap.addMarker(MarkerOptions().position(gdgku).title("Gedung Kuliah Umum - Gedung Perkuliahan ITERA"))

        val gdlabtek = LatLng(-5.360266,105.310294)
        mMap.addMarker(MarkerOptions().position(gdlabtek).title("Laboratorium Teknik - Gedung Perkuliahan ITERA"))
    }
}
