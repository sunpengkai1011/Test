package com.longyuan.test.gallery

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.longyuan.test.R
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity: AppCompatActivity() {
    val colors = mutableListOf<Int>()
    private lateinit var cardScaleHelper: CardScaleHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        for (i in 1..3) {
            colors.add(R.drawable.icon_1)
            colors.add(R.drawable.icon_2)
            colors.add(R.drawable.icon_3)
            colors.add(R.drawable.icon_4)
        }

        initSpeedRecyclerView(srvGallery)
    }

    private fun initSpeedRecyclerView(recyclerView: SpeedRecyclerView){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@GalleryActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = GalleryAdapter(this@GalleryActivity, colors)
        }

        cardScaleHelper = CardScaleHelper()
        cardScaleHelper.setCurrentPos(1)
        cardScaleHelper.attachToRecyclerView(recyclerView)
    }
}