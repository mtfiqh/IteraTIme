package com.itera.iteratime.ui.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
class SabtuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_viewjadwal, container, false)
        val rView = v.findViewById<RecyclerView>(R.id.recycleView)

        rView.layoutManager = LinearLayoutManager(v.context, LinearLayout.VERTICAL, false)
        var jadwalList = ArrayList<Jadwal>()
        var adapter = JadwalAdapterR(jadwalList)

        val database = FirebaseDatabase.getInstance();
        val myRef = database.getReference().ref.child("Jadwal").child("Sabtu")

        val jadwalListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                print(dataSnapshot)
                if(dataSnapshot!!.exists()){
                    for(j in dataSnapshot.children){
                        val jadwal = j.getValue(Jadwal::class.java)
                        jadwal?.key =j.key

//                        println(j.key)
//                        println(jadwal.toString())
//                        jadwalList.add(j.getValue(Jadwal::class.java)!!)
                        println("check")
                        jadwalList.add(jadwal!!)
                        println("size: "+jadwalList.size)
                        println("===============================")
                        rView.adapter=adapter
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        myRef.addValueEventListener(jadwalListener)


        adapter = JadwalAdapterR(jadwalList)
        println("size")
        println(jadwalList.size)
        println("=========================")


        return v
    }


}
