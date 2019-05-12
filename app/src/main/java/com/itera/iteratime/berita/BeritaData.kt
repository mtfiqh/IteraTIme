package com.itera.iteratime.berita

import java.util.ArrayList


class BeritaData {
    var data = arrayOf(
        arrayOf("Informasi Kelas Pengembangan Aplikasi Mobile", "Hadir dikelas", "https://i1.wp.com/de-tekno.com/wp-content/uploads/2015/11/Android-Apple-pie.jpg?resize=279%2C266"),
        arrayOf("Informasi Kelas Proyek Perangkat Lunak", "Tidak ada kuliah", "https://i0.wp.com/de-tekno.com/wp-content/uploads/2015/11/Android-Banana-bread.jpg?resize=285%2C240"),
        arrayOf("Infromasi Kelas Sistem Informasi", "Hadir dikelas", "https://i1.wp.com/de-tekno.com/wp-content/uploads/2015/11/Android-cupcake.jpg?resize=250%2C178"),
        arrayOf("Informasi Kelas Teknologi Game", "Hadir dikelas", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/AndroidQlogo.png/800px-AndroidQlogo.png"))

    fun getListData(): ArrayList<String> {
        var berita : Berita? = null
        val list = java.util.ArrayList<String>()
        for (aData in data) {
            berita = Berita()
            berita!!.setname(aData[0])
            berita!!.setremarks(aData[1])
            berita!!.setphoto(aData[2])

            list.add(berita.toString())
        }

        return list
    }



}
