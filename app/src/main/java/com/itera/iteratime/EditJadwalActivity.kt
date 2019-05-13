package com.itera.iteratime

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_jadwal.*
import kotlinx.android.synthetic.main.activity_edit_jadwal.*
import kotlinx.android.synthetic.main.activity_edit_jadwal.day_Spinner
import kotlinx.android.synthetic.main.activity_edit_jadwal.dosen_Text
import kotlinx.android.synthetic.main.activity_edit_jadwal.gedung_spinner
import kotlinx.android.synthetic.main.activity_edit_jadwal.mataKuliah_Text
import kotlinx.android.synthetic.main.activity_edit_jadwal.room_Text
import kotlinx.android.synthetic.main.activity_edit_jadwal.save_button
import kotlinx.android.synthetic.main.activity_edit_jadwal.sks_Text
import kotlinx.android.synthetic.main.activity_edit_jadwal.time2_Text
import kotlinx.android.synthetic.main.activity_edit_jadwal.time_Text
import java.text.SimpleDateFormat
import java.util.*

class EditJadwalActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_jadwal)
        // Adapter untuk spinner hari
        val adapter= ArrayAdapter.createFromResource(this, R.array.hari_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        day_Spinner.adapter=adapter

        // adapter untuk spinner gedung
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.gedung, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gedung_spinner.adapter=adapter2
        time_Text.setOnClickListener(){
            getTime(time_Text)
        }

        time2_Text.setOnClickListener{
            getTime(time2_Text)
        }

        save_button.setOnClickListener{
            save()
        }
        getExtraIntent()
    }

    fun getExtraIntent(){
        mataKuliah_Text.setText(intent.getStringExtra("matkul"))
        sks_Text.setText(intent.getStringExtra("sks"))
        val hari = intent.getStringExtra("hari")
        val setHari : Int
        when (hari){
            "Senin" -> setHari = 0
            "Selasa" -> setHari = 1
            "Rabu" -> setHari = 2
            "Kamis" -> setHari = 3
            "Jumat" -> setHari = 4
            "Sabtu" -> setHari = 5
            else -> setHari = 6
        }
        day_Spinner.setSelection(setHari)

        val setGedung : Int
        when(intent.getStringExtra("gedung")){
            "A" -> setGedung =0
            "B"-> setGedung =1
            "C"-> setGedung =2
            "D"-> setGedung =3
            "E"-> setGedung =4
            "GKU"-> setGedung =5
            else-> setGedung =6
        }
        gedung_spinner.setSelection(setGedung)
        time_Text.text = intent.getStringExtra("dari")
        time2_Text.text = intent.getStringExtra("sampai")

        room_Text.setText(intent.getStringExtra("ruangan"))
        dosen_Text.setText(intent.getStringExtra("dosen"))

    }
    fun getTime(Text : TextView){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { _, h, m ->
            c.set(Calendar.HOUR_OF_DAY, h)
            c.set(Calendar.MINUTE, m)

            Text.text = SimpleDateFormat("HH:mm").format(c.time)
        }),hour,minute,false)
        tpd.show()

    }

    fun save (){
        if(TextUtils.isEmpty(mataKuliah_Text.text)) {
            createToast("Isi Mata Kuliah nya ya")
            mataKuliah_Text.error = "Tidak Bisa Kosong"
            return
        }
        if(TextUtils.isEmpty(sks_Text.text)){
            createToast("Isi SKS nya ya")
            sks_Text.error = "Tidak Bisa Kosong"
            return
        }
        if(TextUtils.isEmpty(room_Text.text)){
            createToast("Isi ruangan nya ya")
            room_Text.error = "Tidak Bisa Kosong"
            return
        }
        var matakuliah = mataKuliah_Text.text
        var sks = sks_Text.text.toString().toInt()
        var hari = day_Spinner.selectedItem.toString()
        var dari = time_Text.text
        var sampai = time2_Text.text
        var gedung = gedung_spinner.selectedItem.toString()
        var ruangan = room_Text.text
        var dosen = dosen_Text.text



        val jadwal = Jadwal(matakuliah.toString(), sks, hari, dari.toString(), sampai.toString(), gedung, ruangan.toString(), dosen.toString())
        toDatabase(jadwal)
    }

    fun toDatabase(jadwal: Jadwal){
        // Write a message to the database
        var loading = ProgressDialog.show(this, null, "please wait...", true, false)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Jadwal").child(jadwal.hari.toString()).child(intent.getStringExtra("key"))

        myRef.setValue(jadwal).addOnCompleteListener {
            loading.hide()
        }
        val intent = Intent(this, JadwalActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Data Berhasil ditambah")
        }
        createToast("Data berhasil ditambah")
        startActivity(intent)
        finish()
    }

    fun createToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
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
