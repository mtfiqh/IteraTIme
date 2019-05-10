package com.itera.iteratime.ui.maps

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.itera.iteratime.R
import com.itera.iteratime.ui.maps.network.ApiServices
import com.itera.iteratime.ui.maps.network.InitLibrary
import com.itera.iteratime.ui.maps.respone.Distance
import com.itera.iteratime.ui.maps.respone.Duration
import com.itera.iteratime.ui.maps.respone.LegsItem
import com.itera.iteratime.ui.maps.respone.ResponseRoute
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    private val API_KEY = "AIzaSyC7xn-9evBs2spLhfzjRMdjLZ4N_GHU5m0"

    private val pickUpLatLng = LatLng(-6.175110, 106.865039) // Jakarta
    private val locationLatLng = LatLng(-6.197301, 106.795951) // Cirebon

    private var tvStartAddress: TextView? = null
    private var tvEndAddress: TextView? = null
    private var tvDuration: TextView? = null
    private var tvDistance: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction)
        // Maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        // Set Title bar
        supportActionBar!!.title = "Direction Maps API"
        // Inisialisasi Widget
        widgetInit()
        // jalankan method
        actionRoute()
    }

    private fun widgetInit() {
        tvStartAddress = findViewById(R.id.tvStartAddress)
        tvEndAddress = findViewById(R.id.tvEndAddress)
        tvDuration = findViewById(R.id.tvDuration)
        tvDistance = findViewById(R.id.tvDistance)
    }

    private fun actionRoute() {
        val lokasiAwal = pickUpLatLng.latitude.toString() + "," + pickUpLatLng.longitude
        val lokasiAkhir = locationLatLng.latitude.toString() + "," + locationLatLng.longitude

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
                    mMap!!.addMarker(MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"))
                    mMap!!.addMarker(MarkerOptions().position(locationLatLng).title("Lokasi Akhir"))
                    // Dapatkan jarak dan waktu
                    val dataDistance = dataLegs?.distance
                    val dataDuration = dataLegs?.duration

                    // Set Nilai Ke Widget
                    tvStartAddress!!.text = "start location : " + dataLegs?.startAddress!!.toString()
                    tvEndAddress!!.text = "end location : " + dataLegs?.endAddress!!.toString()

                    tvDistance!!.text = "distance : " + dataDistance!!.text + " (" + dataDistance.value + ")"
                    tvDuration!!.text = "duration : " + dataDuration!!.text + " (" + dataDuration.value + ")"
                    /** START
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                    val latLongBuilder = LatLngBounds.Builder()
                    latLongBuilder.include(pickUpLatLng)
                    latLongBuilder.include(locationLatLng)

                    // Bounds Coordinata
                    val bounds = latLongBuilder.build()

                    val width = resources.displayMetrics.widthPixels
                    val height = resources.displayMetrics.heightPixels
                    val paddingMap = (width * 0.2).toInt() //jarak dari
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap)
                    mMap!!.animateCamera(cu)

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                }
            }

            override fun onFailure(call: Call<ResponseRoute>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}