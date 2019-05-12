package com.itera.iteratime.berita

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.itera.iteratime.R
import android.support.v7.widget.RecyclerView
import android.R



class beritamain : AppCompatActivity() {
    private val rvCategory: RecyclerView? = null
    private var list = ArrayList<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.itera.iteratime.R.layout.activity_beritamain)
    }

    private fun showRecyclerList() {
        rvCategory.setLayoutManager(LinearLayoutManager(this))
        val listBeritaAdapter = ListBeritaAdapter(this, null)

        listBeritaAdapter.setListBerita(list)
        if (rvCategory != null) {
            rvCategory.setAdapter(listBeritaAdapter)
        }

        list.addAll(BeritaData.getListData());
        showRecyclerList();
    }
}
