package com.run.share.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory


import com.run.share.R

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Tools {

    var IMAGE_NAME = "iv_share_"
    var i = 0

    //根据网络图片url路径保存到本地
    fun saveImageToSdCard(context: Context, image: String, type: Int): File? {
        var success = false
        var file: File? = null
        try {
            file = createStableImageFile(context)

            var bitmap: Bitmap? = null

            if (type == 0) {
                val url = URL(image)
                var conn: HttpURLConnection? = null
                conn = url.openConnection() as HttpURLConnection
                var `is`: InputStream? = null
                `is` = conn.inputStream
                bitmap = BitmapFactory.decodeStream(`is`)

            } else if (type == 1) {
                //  bitmap = QRCodeUtil.createQRCodeBitmap(image, 600, 600);
                bitmap = QRCodeUtil.addLogo(QRCodeUtil.createQRCodeBitmap(image, 480, 480)!!, BitmapFactory.decodeResource(context.resources, R.mipmap.ic_logo))
            }

            val outStream: FileOutputStream
            outStream = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
            success = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (success) {
            file
        } else {
            null
        }
    }

    //创建本地保存路径
    @Throws(IOException::class)
    fun createStableImageFile(context: Context): File {
        i++
        val imageFileName = "$IMAGE_NAME$i.jpg"
        val storageDir = context.externalCacheDir
        return File(storageDir, imageFileName)
    }

    //判断是否安装了微信
    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }
}
