package com.longyuan.test.gallery

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CardLinearSnapHelper: LinearSnapHelper() {
    var mNotToScroll = false

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        if (mNotToScroll){
            return IntArray(2)
        }
        return super.calculateDistanceToFinalSnap(layoutManager, targetView)
    }
}