package com.itera.iteratime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DirectionToGedungActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_to_gedung)
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
//        mMap.addMarker(MarkerOptions().position(itera).title("Institut Teknologi Sumatera"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(itera, 17F))
        var gedung = intent.getStringExtra("gedung")
        var gd = LatLng(0.0, 0.0)
        gd =when(gedung){
            "A" -> LatLng(-5.357919,105.314414)
            "B" -> LatLng(-5.357966,105.315304)
            "C" -> LatLng(-5.358449,105.313568)
            "D" -> LatLng(-5.358800,105.313438)
            "E" -> LatLng(-5.360101,105.315462)
            "GKU" -> LatLng(-5.360876,105.310283)
            else -> LatLng(-5.360266,105.310294)
        }
        mMap.addMarker(MarkerOptions().position(gd).title("Gedung $gedung")).showInfoWindow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.gelap -> {
                showGelap()
            }
            R.id.terang -> {
                showTerang()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun showGelap() {
        application.setTheme(R.style.AppTheme)
    }

    private fun showTerang() {
        application.setTheme(R.style.AppTheme2)

    }
}
