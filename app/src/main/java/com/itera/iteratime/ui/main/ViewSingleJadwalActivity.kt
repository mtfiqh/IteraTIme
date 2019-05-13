package com.itera.iteratime.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.itera.iteratime.*
import kotlinx.android.synthetic.main.activity_add_jadwal.*
import kotlinx.android.synthetic.main.activity_view_single_jadwal.*

class ViewSingleJadwalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_single_jadwal)

        showMap.setOnClickListener {
            val intentMap = Intent(this, DirectionToGedungActivity::class.java)
            intentMap.putExtra("gedung", intent.getStringExtra("gedung"))
            startActivity(intentMap)
        }

        getExtraIntent()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_view_single_jadwal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.Edit -> {
                editJadwal()
                true
            }
            R.id.delete -> {
                deleteJadwal()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun deleteJadwal(){
        var loading = ProgressDialog.show(this, null, "please wait...", true, false)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Jadwal").child(intent.getStringExtra("hari")).child(intent.getStringExtra("key"))

        myRef.removeValue().addOnCompleteListener {
            loading.hide()
        }
        val intent = Intent(this, JadwalActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Data Berhasil dihapus")
        }
        createToast("Data berhasil dihapus")
        startActivity(intent)
        finish()
    }

    fun editJadwal(){

        val intentEdit = Intent(this, EditJadwalActivity::class.java)
        intentEdit.putExtra("key", intent.getStringExtra("key"))
        intentEdit.putExtra("matkul", matakuliah.text.toString())
        intentEdit.putExtra("sks", sks.text.toString())
        intentEdit.putExtra("hari", intent.getStringExtra("hari"))
        intentEdit.putExtra("dari", dari.text.toString())
        intentEdit.putExtra("sampai", sampai.text.toString())
        intentEdit.putExtra("gedung", gedung.text.toString())
        intentEdit.putExtra("ruangan", ruangan.text.toString())
        intentEdit.putExtra("dosen", dosen.text.toString())
        startActivity(intentEdit)
    }

    fun getExtraIntent(){
        matakuliah.text = intent.getStringExtra("matkul")
        sks.text = intent.getStringExtra("sks")
        dari.text = intent.getStringExtra("dari")
        sampai.text = intent.getStringExtra("sampai")
        dosen.text = intent.getStringExtra("dosen")
        gedung.text = intent.getStringExtra("gedung")
        ruangan.text = intent.getStringExtra("ruangan")
        hari.text = intent.getStringExtra("hari")
    }


    fun createToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
