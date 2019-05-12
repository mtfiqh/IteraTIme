package com.itera.iteratime

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, JadwalActivity::class.java)
        // start your next activity
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.about_us -> Toast.makeText(this,"Selected About Us", Toast.LENGTH_LONG).show()

            R.id.faq -> Toast.makeText(this,"Selected FAQ", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
