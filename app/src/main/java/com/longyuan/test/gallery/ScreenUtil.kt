package com.longyuan.test.gallery

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

class ScreenUtil {

    companion object{

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        fun getScreenWidth(context: Context): Int{
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            windowManager.defaultDisplay.getSize(point)
            return point.x
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        fun getScreenHeight(context: Context): Int{
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            windowManager.defaultDisplay.getSize(point)
            return point.y
        }

        fun dip2Px(context: Context, dpValue: Float): Int{
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2Dip(context: Context, pxValue: Float): Int{
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}