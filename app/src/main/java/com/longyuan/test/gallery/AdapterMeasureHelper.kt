package com.longyuan.test.gallery

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterMeasureHelper {
    private var mPagePadding: Int = 15
    private var mShowLeftCardWidth: Int = 60

    fun onCreateView(parent: ViewGroup, itemView: View){
        val layoutParams = itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.width = parent.width - ScreenUtil.dip2Px(itemView.context, (2 * (mPagePadding + mShowLeftCardWidth)).toFloat())
        itemView.layoutParams = layoutParams
    }

    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int){
        val padding = ScreenUtil.dip2Px(itemView.context, mPagePadding.toFloat())
        itemView.setPadding(padding, 0, padding, 0)
        val leftMargin = if (position == 0) padding + ScreenUtil.dip2Px(itemView.context, mShowLeftCardWidth.toFloat()) else 0
        val rightMargin = if (position == itemCount - 1) padding + ScreenUtil.dip2Px(itemView.context, mShowLeftCardWidth.toFloat()) else 0
        setViewMargin(itemView, leftMargin, 0, rightMargin, 0)
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int){
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        if (layoutParams.leftMargin != left || layoutParams.rightMargin != right||
                layoutParams.topMargin != top || layoutParams.bottomMargin != bottom){
            layoutParams.setMargins(left, top, right, bottom)
            view.layoutParams = layoutParams
        }
    }

    fun setPagePadding(pagePadding: Int){
        mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(cardWidth: Int){
        mShowLeftCardWidth = cardWidth
    }
}