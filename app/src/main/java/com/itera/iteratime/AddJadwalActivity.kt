package com.itera.iteratime

import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add_jadwal.*
import java.text.SimpleDateFormat
import java.util.*

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
            getTime()
        }

        save_button.setOnClickListener{
            save()
        }

    }

    fun getTime(){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { v, h, m ->
            c.set(Calendar.HOUR_OF_DAY, h)
            c.set(Calendar.MINUTE, m)

            time_Text.text = SimpleDateFormat("HH:mm").format(c.time)
        }),hour,minute,false)
        tpd.show()

    }

    fun save (){
        var matakuliah = mataKuliah_Text.text
        var sks = sks_Text.text.toString().toInt()
        var hari = day_Spinner.selectedItem.toString()
        var pukul = time_Text.text
        var gedung = gedung_spinner.selectedItem.toString()
        var ruangan = room_Text.text
        var dosen = dosen_Text.text

        var jadwal = Jadwal(matakuliah.toString(), sks, hari, pukul.toString(), gedung, ruangan.toString(), dosen.toString())

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
