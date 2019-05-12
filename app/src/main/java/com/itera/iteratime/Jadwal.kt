package com.itera.iteratime

class Jadwal(){
    var mataKuliah: String?=""
    var sks: Int?=0
    var hari: String?=""
    var dari:String?=""
    var sampai:String?=""
    var gedung:String?=""
    var ruangan:String?=""
    var dosen:String?=""
    var key:String?=null
    constructor(mataKuliah: String, sks: Int, hari: String, dari:String, sampai:String, gedung:String,ruangan:String, dosen:String) : this(){
        this.mataKuliah = mataKuliah
        this.sks=sks
        this.hari=hari
        this.dari=dari
        this.sampai=sampai
        this.gedung=gedung
        this.ruangan=ruangan
        this.dosen=dosen
    }
    constructor(key:String, mataKuliah: String, sks: Int, hari: String, dari:String, sampai:String, gedung:String,ruangan:String, dosen:String) : this(){
        this.key=key
        this.mataKuliah = mataKuliah
        this.sks=sks
        this.hari=hari
        this.dari=dari
        this.sampai=sampai
        this.gedung=gedung
        this.ruangan=ruangan
        this.dosen=dosen
    }


    override fun toString(): String {
        return "Key: "+key+"\n" +
                ""+"Mata Kuliah: "+mataKuliah+"\n" +
                "sks: "+sks+"\n" +
                "Hari: "+hari+"\n"+"" +
                "dari: "+dari
    }
}