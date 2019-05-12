package com.itera.iteratime

class Jadwal(
    var mataKuliah: String,
    var sks: Int,
    var hari: String,
    var dari:String,
    var sampai:String,
    var gedung:String,
    var ruangan:String,
    var dosen:String
){
    constructor() : this("", 0, "", "", "", "", "", "")

    override fun toString(): String {
        return "Mata Kuliah: "+mataKuliah+"\n" +
                "sks: "+sks+"\n" +
                "Hari: "+hari+"\n"+"" +
                "dari: "+dari
    }
}