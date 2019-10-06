package com.longyuan.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val disposes = mutableListOf<Disposable>()
    companion object{
        const val SHAREDNAME = "path"
        const val REQUST_PERMISSION_CODE = 1
    }

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
//                != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(permissions, REQUST_PERMISSION_CODE)
//            }
//        }
        val sharedPreferences = getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE)
        val path = sharedPreferences.getString(SHAREDNAME, "")
        var bitmap: Bitmap
        if (path.isNotEmpty()){
            disposes.add(Single.create<Bitmap> {
                val file = File(path)
                if(file.exists()){
                    val bytes = file.readBytes()
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    it.onSuccess(bitmap)
                }else{
                    it.onError(Exception())
                }
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        imgSignature.setImageBitmap(it)
                    },{

                    }
                )
            )
        }


        fab.setOnClickListener {
            val path = Environment.getExternalStorageDirectory().path + "/signature.png"
            sgvMain.save(path).subscribe({
                sharedPreferences.edit {
                    putString(SHAREDNAME, it)
                }
                Toast.makeText(this, "save succeed, path: $it", Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this, "save failed", Toast.LENGTH_SHORT).show()
            })
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUST_PERMISSION_CODE) {
//           permissions.forEach {
//               Log.d("test", it)
//           }
//        }
//    }
}
