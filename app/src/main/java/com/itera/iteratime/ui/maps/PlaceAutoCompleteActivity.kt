package com.itera.iteratime.ui.maps

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.model.LatLng
import com.itera.iteratime.R

class PlaceAutoCompleteActivity : AppCompatActivity() {

    // Deklarasi variable
    private var tvPickUpFrom: TextView? = null
    private var tvDestLocation: TextView? = null
    private var tvPickUpAddr: TextView? = null
    private var tvPickUpLatLng: TextView? = null
    private var tvPickUpName: TextView? = null
    private var tvDestLocAddr: TextView? = null
    private var tvDestLocLatLng: TextView? = null
    private var tvDestLocName: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_auto_complete)

        supportActionBar!!.setTitle("Place Auto Complete")
        // Inisialisasi Widget
        wigetInit()

        // Event OnClick
        tvPickUpFrom!!.setOnClickListener {
            // Jalankan Method untuk menampilkan Place Auto Complete
            // Bawa constant PICK_UP
            showPlaceAutoComplete(PICK_UP)
        }
        // Event OnClick
        tvDestLocation!!.setOnClickListener {
            // Jalankan Method untuk menampilkan Place Auto Complete
            // Bawa constant DEST_LOC
            showPlaceAutoComplete(DEST_LOC)
        }
    }

    // Method untuk Inisilisasi Widget agar lebih rapih
    private fun wigetInit() {
        tvPickUpFrom = findViewById(R.id.tvPickUpFrom)
        tvDestLocation = findViewById(R.id.tvDestLocation)
        tvPickUpAddr = findViewById(R.id.tvPickUpAddr)
        tvPickUpLatLng = findViewById(R.id.tvPickUpLatLng)
        tvPickUpName = findViewById(R.id.tvPickUpName)
        tvDestLocAddr = findViewById(R.id.tvDestLocAddr)
        tvDestLocLatLng = findViewById(R.id.tvDestLocLatLng)
        tvDestLocName = findViewById(R.id.tvDestLocName)
    }

    // Method menampilkan input Place Auto Complete
    private fun showPlaceAutoComplete(typeLocation: Int) {
        // isi RESUT_CODE tergantung tipe lokasi yg dipilih.
        // titik jmput atau tujuan
        REQUEST_CODE = typeLocation

        // Filter hanya tmpat yg ada di Indonesia
        val typeFilter = AutocompleteFilter.Builder().setCountry("ID").build()
        try {
            // Intent untuk mengirim Implisit Intent
            val mIntent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                .setFilter(typeFilter)
                .build(this)
            // jalankan intent impilist
            startActivityForResult(mIntent, REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace() // cetak error
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace() // cetak error
            // Display Toast
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if (resultCode == Activity.RESULT_OK) {
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // Tampung Data tempat ke variable
            val placeData = PlaceAutocomplete.getPlace(this, data!!)

            if (placeData.isDataValid) {
                // Show in Log Cat
                Log.d("autoCompletePlace Data", placeData.toString())

                // Dapatkan Detail
                val placeAddress = placeData.address!!.toString()
                val placeLatLng = placeData.latLng
                val placeName = placeData.name.toString()

                // Cek user milih titik jemput atau titik tujuan
                when (REQUEST_CODE) {
                    PICK_UP -> {
                        // Set ke widget lokasi asal
                        tvPickUpFrom!!.text = placeAddress
                        tvPickUpAddr!!.text = "Location Address : $placeAddress"
                        tvPickUpLatLng!!.text = "LatLang : $placeLatLng"
                        tvPickUpName!!.text = "Place Name : $placeName"
                    }
                    DEST_LOC -> {
                        // Set ke widget lokasi tujuan
                        tvDestLocation!!.text = placeAddress
                        tvDestLocAddr!!.text = "Destination Address : $placeAddress"
                        tvDestLocLatLng!!.text = "LatLang : $placeLatLng"
                        tvDestLocName!!.text = "Place Name : $placeName"
                    }
                }

            } else {
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        val PICK_UP = 0
        val DEST_LOC = 1
        private var REQUEST_CODE = 0
    }
}