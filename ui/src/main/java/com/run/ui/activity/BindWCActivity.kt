package com.run.ui.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseActivity
import com.run.common.view.MyBottomSheetDialog
import com.run.share.utils.QRCodeUtil
import com.run.ui.R
import java.io.*

class BindWCActivity : BaseActivity<Nothing>(), View.OnLongClickListener {


    companion object {
        fun newInstance(activity: Activity, url: String) {
            val intent = Intent(activity, BindWCActivity::class.java)
            intent.putExtra("URL", url)
            activity.startActivity(intent)
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_bind_wc
    }
    private var iv_code: ImageView? = null
    private var mBitmap: Bitmap? = null
    private var url: String? = null
    override fun initViews() {
        iv_code = findViewById(R.id.iv_code)
        iv_code!!.setOnLongClickListener(this)
        findViewById<View>(R.id.tv_back).setOnClickListener({ finish() })
    }

    override fun initData() {
        url = intent.getStringExtra("URL")
        mBitmap = QRCodeUtil.createQRCodeBitmap(url!!, 480, 480)
        mBitmap = QRCodeUtil.addLogo(QRCodeUtil.createQRCodeBitmap(url!!, 480, 480)!!, BitmapFactory.decodeResource(resources, R.mipmap.ic_logo))
        if (mBitmap == null) return
        iv_code!!.setImageBitmap(mBitmap)
    }

    override fun onLongClick(v: View?): Boolean {
        showSaveDialog()
        return false
    }

    private fun showSaveDialog() {
        val dialog = MyBottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_save_img_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.ll_save).setOnClickListener(View.OnClickListener {
            //保存
            dialog.cancel()
            if (mBitmap == null) return@OnClickListener
            saveImageToGallery(this@BindWCActivity, mBitmap!!)
        })
        view.findViewById<View>(R.id.ll_copy).setOnClickListener(View.OnClickListener {
            //复制链接
            dialog.cancel()
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = url
            showMsg("链接复制成功!")
        })
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener(View.OnClickListener { dialog.cancel() })
        dialog.show()
    }


    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    fun saveImageToGallery(context: Context, bmp: Bitmap) {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "image")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
            showMsg("保存成功")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver,
                    file.absolutePath, fileName, null)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        // 最后通知图库更新
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
    }



    override fun initPresenter(): Nothing? {
        return null
    }


}
