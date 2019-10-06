package com.longyuan.test.gallery

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class SpeedRecyclerView: RecyclerView {

    companion object{
        const val FLING_SCALE_DOWN_FACTOR = 0.5f
        const val FLING_MAX_VELOCITY = 8000
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int): super(context, attributeSet, defStyle)

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val vX = solveVelocity(velocityX)
        val vY = solveVelocity(velocityY)
        return super.fling(vX, vY)

    }

    private fun solveVelocity(velocity: Int): Int {
        if(velocity > 0){
            return min(velocity, FLING_MAX_VELOCITY)
        }else {
            return max(velocity, -FLING_MAX_VELOCITY)
        }
    }
}