package com.itera.iteratime

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.TextUtils
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_jadwal.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class AddJadwalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_jadwal)

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

    }

    fun getTime(Text : TextView){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { _, h, m ->
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
        val myRef = database.getReference("Jadwal")

        myRef.child(jadwal.hari.toString()).push().setValue(jadwal).addOnCompleteListener {
            loading.hide()
        }
        val intent = Intent(this, JadwalActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "Data Berhasil ditambah")
        }
        createToast("Data berhasil ditambah")
        startActivity(intent)
        finish()
    }

    fun createToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


//    fun getTime(textView: TextView, context: Context){
//
//        val cal = Calendar.getInstance()
//
//        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
//            cal.set(Calendar.HOUR_OF_DAY, hour)
//            cal.set(Calendar.MINUTE, minute)
//
//            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
//        }
//
//        textView.setOnClickListener {
//            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
//        }
//    }

}
