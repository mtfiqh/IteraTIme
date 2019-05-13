package com.itera.iteratime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gelap.setOnClickListener{
            setTheme(R.style.AppTheme)
        }

        terang.setOnClickListener{
            setTheme(R.style.AppTheme2)
        }
        setContentView(R.layout.activity_setting)
    }
}
