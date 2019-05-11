package com.itera.iteratime.ui.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.FirebaseDatabase
import com.itera.iteratime.Jadwal
import com.itera.iteratime.JadwalAdapterR

import com.itera.iteratime.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SeninFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_senin, container, false)
        val rView = v.findViewById<RecyclerView>(R.id.recycleView)

        rView.layoutManager = LinearLayoutManager(v.context, LinearLayout.VERTICAL, false)
        val jadwalList = ArrayList<Jadwal>()

        
        jadwalList.add(Jadwal("x", 2, "Senin", "10:00", "12:00", "C", "204", "iqbal" ))
        val adapter=JadwalAdapterR(jadwalList)

        rView.adapter=adapter
        return v
    }



}
