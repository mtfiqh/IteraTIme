package com.itera.iteratime.berita

import android.os.Parcel
import android.os.Parcelable

class Berita(){
    var name: String = ""

    fun setname(name : String){
        this.name = name
    }

    fun getname(): String {
        return this.name
    }

    var remarks: String = ""
    fun setremarks(remarks : String){
        this.remarks = remarks
    }

    fun getremarks(): String {
        return this.remarks
    }

    var photo: String = ""
    fun setphoto(photo : String){
        this.photo = photo
    }

    fun getphoto(): String {
        return this.photo
    }
}