package com.longyuan.test.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.longyuan.test.R
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter(private val context: Context, private val datas: List<Int>): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private val adapterMeasureHelper = AdapterMeasureHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
        adapterMeasureHelper.onCreateView(parent, view)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterMeasureHelper.onBindViewHolder(holder.itemView, position, itemCount)
        holder.itemView.imgGallery.setImageResource(datas[position])
        holder.itemView.imgGallery.setOnClickListener { Toast.makeText(context, "this is $position picture", Toast.LENGTH_SHORT).show() }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}