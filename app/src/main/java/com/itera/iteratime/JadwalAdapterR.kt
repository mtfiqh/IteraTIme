package com.itera.iteratime

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text

class JadwalAdapterR(val jadwalList: ArrayList<Jadwal>) : RecyclerView.Adapter<JadwalAdapterR.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_jadwal,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        //get size
        return jadwalList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val jadwal:Jadwal = jadwalList[p1]

        p0?.dari.text = jadwal.dari
        p0.sampai.text = jadwal.sampai
        p0.sks.text = jadwal.sks.toString()
        p0.matkul.text = jadwal.mataKuliah
        p0.gedung.text=jadwal.gedung
        p0.ruangan.text=jadwal.ruangan
        p0.dosen.text=jadwal.dosen

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val matkul = itemView.findViewById(R.id.matakuliah) as TextView
        val sks = itemView.findViewById(R.id.sks) as TextView
        val dari = itemView.findViewById(R.id.dari) as TextView
        val sampai = itemView.findViewById(R.id.sampai) as TextView
        val gedung = itemView.findViewById<TextView>(R.id.gedung)
        val ruangan = itemView.findViewById<TextView>(R.id.ruangan)
        val dosen = itemView.findViewById<TextView>(R.id.dosen)
    }
}