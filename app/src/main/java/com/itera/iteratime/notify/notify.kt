package com.itera.iteratime.notify

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.itera.iteratime.R
import kotlinx.android.synthetic.main.activity_notify.*

class notify : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        getExtraIntent()
    }

    fun getExtraIntent(){
//        matakuliah.text = intent.getStringExtra("matkul")
//        sks.text = intent.getStringExtra("sks")
//        dari.text = intent.getStringExtra("dari")
//        sampai.text = intent.getStringExtra("sampai")
//        dosen.text = intent.getStringExtra("dosen")
//        gedung.text = intent.getStringExtra("gedung")
//        ruangan.text = intent.getStringExtra("ruangan")
    }
}
