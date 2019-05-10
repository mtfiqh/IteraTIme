package com.itera.iteratime.ui.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.itera.iteratime.R
import com.itera.iteratime.ui.maps.network.InitLibrary
import com.itera.iteratime.ui.maps.respone.ResponseRoute
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoToActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    private val API_KEY = "AIzaSyC7xn-9evBs2spLhfzjRMdjLZ4N_GHU5m0"

    var pickUpLatLng: LatLng? = null
    var locationLatLng: LatLng? = null

    private val tvStartAddress: TextView? = null
    private val tvEndAddress: TextView? = null
    private var tvPrice: TextView? = null
    private var tvDistance: TextView? = null
    private var btnNext: Button? = null
    private var infoPanel: LinearLayout? = null

    // Deklarasi variable
    private var tvPickUpFrom: TextView? = null
    private var tvDestLocation: TextView? = null

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.setPadding(10, 180, 10, 10)
        mMap!!.mapType = com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL

        mMap!!.isMyLocationEnabled = true
        mMap!!.uiSettings.isCompassEnabled = true
        mMap!!.uiSettings.isZoomGesturesEnabled = true
        mMap!!.uiSettings.isRotateGesturesEnabled = false
        mMap!!.uiSettings.isZoomControlsEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to)

        getSupportActionBar()?.setTitle("Route")

        // Inisialisasi Widget
        wigetInit()
        infoPanel!!.setVisibility(View.GONE)
        // Event OnClick
        tvPickUpFrom!!.setOnClickListener(View.OnClickListener {
            // Jalankan Method untuk menampilkan Place Auto Complete
            // Bawa constant PICK_UP
            showPlaceAutoComplete(GoToActivity.PICK_UP)
        })
        // Event OnClick
        tvDestLocation!!.setOnClickListener(View.OnClickListener {
            // Jalankan Method untuk menampilkan Place Auto Complete
            // Bawa constant DEST_LOC
            showPlaceAutoComplete(GoToActivity.DEST_LOC)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // Tampung Data tempat ke variable
            val placeData = PlaceAutocomplete.getPlace(this, data)

            if (placeData.isDataValid) {
                // Show in Log Cat
                Log.d("autoCompletePlace Data", placeData.toString())

                // Dapatkan Detail
                val placeAddress = placeData.address.toString()
                val placeLatLng = placeData.latLng
                val placeName = placeData.name.toString()

                // Cek user milih titik jemput atau titik tujuan
                when (GoToActivity.REQUEST_CODE) {
                    GoToActivity.PICK_UP -> {
                        // Set ke widget lokasi asal
                        tvPickUpFrom!!.setText(placeAddress)
                        pickUpLatLng = placeData.latLng
                    }
                    GoToActivity.DEST_LOC -> {
                        // Set ke widget lokasi tujuan
                        tvDestLocation!!.setText(placeAddress)
                        locationLatLng = placeData.latLng
                    }
                }
                // Jalankan Action Route
                if (pickUpLatLng != null && locationLatLng != null) {
                    actionRoute(placeLatLng, GoToActivity.REQUEST_CODE)
                }

            } else {
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place !", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //Fungsi
    private fun wigetInit() {
        // Maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        infoPanel = findViewById(R.id.infoPanel)
        // Widget
        tvPickUpFrom = findViewById(R.id.tvPickUpFrom)
        tvDestLocation = findViewById(R.id.tvDestLocation)

        tvPrice = findViewById(R.id.tvPrice)
        tvDistance = findViewById(R.id.tvDistance)
        btnNext = findViewById(R.id.btnNext)
    }

    companion object {

        val PICK_UP = 0
        val DEST_LOC = 1
        var REQUEST_CODE = 0
    }

    private fun actionRoute(placeLatLng: LatLng, requestCode: Int) {
        val lokasiAwal = pickUpLatLng!!.latitude.toString() + "," + pickUpLatLng!!.longitude
        val lokasiAkhir = locationLatLng!!.latitude.toString() + "," + locationLatLng!!.longitude

        // Clear dulu Map nya
        mMap!!.clear()
        // Panggil Retrofit
        val api = InitLibrary.instance
        // Siapkan request
        val routeRequest = api.request_route(lokasiAwal, lokasiAkhir, API_KEY)
        // kirim request
        routeRequest.enqueue(object : Callback<ResponseRoute> {
            override fun onResponse(call: Call<ResponseRoute>, response: Response<ResponseRoute>) {

                if (response.isSuccessful) {
                    // tampung response ke variable
                    val dataDirection = response.body()

                    val dataLegs = dataDirection!!.routes!![0]?.legs!![0]

                    // Dapatkan garis polyline
                    val polylinePoint = dataDirection.routes!![0]?.overviewPolyline!!.points
                    // Decode
                    val decodePath = PolyUtil.decode(polylinePoint!!)
                    // Gambar garis ke maps
                    mMap!!.addPolyline(
                        PolylineOptions().addAll(decodePath)
                            .width(8f).color(Color.argb(255, 56, 167, 252))
                    ).isGeodesic = true

                    // Tambah Marker
                    mMap!!.addMarker(MarkerOptions().position(pickUpLatLng!!).title("Lokasi Awal"))
                    mMap!!.addMarker(MarkerOptions().position(locationLatLng!!).title("Lokasi Akhir"))
                    // Dapatkan jarak dan waktu
                    val dataDistance = dataLegs?.distance
                    val dataDuration = dataLegs?.duration

                    // Set Nilai Ke Widget
                    val price_per_meter = 250.0
                    val priceTotal = price_per_meter * dataDistance!!.getValue() // Jarak * harga permeter

                    tvDistance.setText(dataDistance?.getText())
                    tvPrice!!.setText(priceTotal.toString())
                    /** START
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                    val latLongBuilder = LatLngBounds.Builder()
                    latLongBuilder.include(pickUpLatLng!!)
                    latLongBuilder.include(locationLatLng!!)

                    // Bounds Coordinata
                    val bounds = latLongBuilder.build()

                    val width = getResources().getDisplayMetrics().widthPixels
                    val height = getResources().getDisplayMetrics().heightPixels
                    val paddingMap = (width * 0.2).toInt() //jarak dari
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap)
                    mMap!!.animateCamera(cu)

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */
                    // Tampilkan info panel
                    infoPanel!!.setVisibility(View.VISIBLE)

                    mMap!!.setPadding(10, 180, 10, 180)

                }
            }

            override fun onFailure(call: Call<ResponseRoute>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun showPlaceAutoComplete(typeLocation: Int) {
        // isi RESUT_CODE tergantung tipe lokasi yg dipilih.
        // titik jmput atau tujuan
        GoToActivity.REQUEST_CODE = typeLocation

        // Filter hanya tmpat yg ada di Indonesia
        val typeFilter = AutocompleteFilter.Builder().setCountry("ID").build()
        try {
            // Intent untuk mengirim Implisit Intent
            val mIntent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                .setFilter(typeFilter)
                .build(this)
            // jalankan intent impilist
            startActivityForResult(mIntent, GoToActivity.REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace() // cetak error
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace() // cetak error
            // Display Toast
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show()
        }

    }
}
