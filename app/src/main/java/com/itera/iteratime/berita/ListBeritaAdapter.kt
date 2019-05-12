package com.itera.iteratime.berita

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itera.iteratime.R
import java.util.ArrayList
import com.itera.iteratime.berita.Berita as Berita

class ListBeritaAdapter : RecyclerView.Adapter<ListBeritaAdapter.CategoryViewHolder> {
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    var listBerita : ArrayList<Berita>

    fun setListBerita(listBerita : ArrayList<Berita>) {
        this.listBerita = listBerita
    }

    fun getListBerita(): ArrayList<Berita> {
        return this!!.listBerita!!
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoryViewHolder {
        val itemRow = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_berita, viewGroup, false)
        return CategoryViewHolder(itemRow)
    }

    override fun onBindViewHolder(categoryViewHolder: CategoryViewHolder, position: Int) {
        categoryViewHolder.tvName.setText(listBerita!![position].getname())
        categoryViewHolder.tvRemarks.setText(listBerita!![position].getremarks())
        Glide.with(context)
            .load(listBerita!![position].getphoto())
            .apply(RequestOptions().override(55, 55))
            .into(categoryViewHolder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listBerita!!.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvName: TextView
        internal var tvRemarks: TextView
        internal var imgPhoto: ImageView

        init {
            tvName = itemView.findViewById(R.id.tv_item_name)
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks)
            imgPhoto = itemView.findViewById(R.id.img_item_photo)
        }
    }
}
