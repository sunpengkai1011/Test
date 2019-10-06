package com.longyuan.test.gallery

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.max

class CardScaleHelper {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContext: Context

    private var mScale = 0.8f
    private var mPagePadding = 15
    private var mShowLeftCardWidth = 60

    private var mCardWidth = 0
    private var mPageWidth = 0
    private var mCardGalleryWidth = 0

    private var mCurrentPos = 0
    private var mCurrentOffset = 0

    private val mLinearSnapHelper = CardLinearSnapHelper()

    fun attachToRecyclerView(recyclerView: RecyclerView){
        mRecyclerView = recyclerView
        mContext = mRecyclerView.context
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNotToScroll =
                        mCurrentOffset == 0 || mCurrentOffset == mRecyclerView.adapter?.itemCount?.minus(1)?.let {
                            getDestItemOffset(it)
                        }
                }else {
                    mLinearSnapHelper.mNotToScroll = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx != 0){
                    mCurrentOffset += dx
                    computeCurrentPos()
                    onScrollChangeCallback()
                }
            }
        })

        initWidth()
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView)
    }

    private fun initWidth(){
        mRecyclerView.post {
            mCardGalleryWidth = mRecyclerView.width
            mCardWidth = mCardGalleryWidth - ScreenUtil.dip2Px(mContext, 2 * (mPagePadding + mShowLeftCardWidth).toFloat())
            mPageWidth = mCardWidth
            mRecyclerView.smoothScrollToPosition(mCurrentPos)
            onScrollChangeCallback()
        }
    }

    private fun getDestItemOffset(destPos: Int): Int{
        return mPageWidth * destPos
    }

    private fun computeCurrentPos(){
        if (mPageWidth <= 0) return
        var pageChanged = false
        if (abs(mCurrentOffset - mCurrentPos * mPageWidth) >= mPageWidth)
            pageChanged = true

        if (pageChanged){
            val tempPos = mCurrentPos
            mCurrentPos = mCurrentOffset / mPageWidth
        }
    }

    private fun onScrollChangeCallback(){
        val offset = mCurrentOffset - mCurrentPos * mPageWidth
        val percent = max(abs(offset) * 1.0 / mPageWidth, 0.0001).toFloat()

        var leftView: View? = null
        val currentView = mRecyclerView.layoutManager?.findViewByPosition(mCurrentPos)
        var rightView: View? = null

        if (mCurrentPos > 0)
           leftView = mRecyclerView.layoutManager?.findViewByPosition(mCurrentPos - 1)
        if (mCurrentPos < mRecyclerView.adapter?.itemCount?.minus(1)!!)
           rightView = mRecyclerView.layoutManager?.findViewByPosition(mCurrentPos + 1)

        leftView?.scaleY = (1 - mScale) * percent + mScale
        rightView?.scaleY = (1 - mScale) * percent + mScale
        currentView?.scaleY = (mScale - 1) * percent + mScale
    }


    fun setCurrentPos(currentPos: Int){
        mCurrentPos = currentPos
    }

    fun getCurrentPos(): Int{
        return mCurrentPos
    }

    fun setScale(scale: Float){
        mScale = scale
    }

    fun setPagePadding(pagePadding: Int){
        mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int){
        mShowLeftCardWidth = showLeftCardWidth
    }
}