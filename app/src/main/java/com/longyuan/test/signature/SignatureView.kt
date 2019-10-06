package com.longyuan.test.signature

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class SignatureView: View {

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    val paint = Paint()
    val path = Path()
    lateinit var canvas: Canvas
    lateinit var bitmap: Bitmap
    var paintWidth = 10f
    var paintColor = Color.BLACK
    var backColor = Color.TRANSPARENT

    init {
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = paintWidth
            color = paintColor
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        canvas.drawColor(backColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
        canvas?.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> touchDown(event)
            MotionEvent.ACTION_MOVE -> touchMove(event)
            MotionEvent.ACTION_UP -> {
                canvas.drawPath(path, paint)
                path.reset()
            }
        }
        invalidate()
        return true
    }

    private fun touchDown(event: MotionEvent?){
        path.reset()
        val x = event?.x?: 0f
        val y = event?.y?: 0f
        path.moveTo(x, y)
    }

    private fun touchMove(event: MotionEvent?){
        val x = event?.x?: 0f
        val y = event?.y?: 0f
        path.lineTo(x, y)
    }

    fun clear(){
        canvas?.drawColor(backColor, PorterDuff.Mode.CLEAR)
        paint.color = paintColor
        invalidate()
    }

    fun save(path: String): Single<String>{
        return Single.create<String> {
            try {
                val file = File(path)
                if (file.exists()){
                    file.delete()
                }
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
            }catch (e: Exception){
                e.printStackTrace()
                it.onError(e)
                return@create
            }
            it.onSuccess(path)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setPWidth(width: Float){
        paintWidth = if (width > 0) width else 10f
        paint.strokeWidth = paintWidth
    }

    fun setBColor(@ColorInt color: Int){
        backColor = color
    }

    fun setPColor(@ColorInt color: Int){
        paintColor = color
        paint.color = paintColor
    }
}