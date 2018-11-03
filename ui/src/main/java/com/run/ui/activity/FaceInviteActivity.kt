package com.run.ui.activity

import android.app.Activity
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
import com.run.common.utils.UStatusBar
import com.run.common.view.MyBottomSheetDialog
import com.run.share.UShare
import com.run.share.utils.QRCodeUtil
import com.run.ui.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class FaceInviteActivity : BaseActivity<Nothing>(), View.OnLongClickListener {

    companion object {
        fun newInstance(activity: Activity, url: String, title: String, content: String, picture: String) {
            val intent = Intent(activity, FaceInviteActivity::class.java)
            intent.putExtra("URL", url)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("picture", picture)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_face_invite
    }

    private lateinit var rl_root: View
    private lateinit var iv_card: ImageView
    override fun initViews() {
        UStatusBar.setStatusBarTranslucent(this)
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
        iv_card = findViewById(R.id.iv_card)
        rl_root = findViewById(R.id.rl_root)
        rl_root.setOnLongClickListener(this)
    }


    private var title: String? = null
    private var content: String? = null
    private var picture: String? = null
    private var mBitmap: Bitmap? = null
    private var url: String? = null

    override fun initData() {
        url = intent.getStringExtra("URL")
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        picture = intent.getStringExtra("picture")
        mBitmap = QRCodeUtil.addLogo(QRCodeUtil.createQRCodeBitmap(this.url!!, 480, 480)!!, BitmapFactory.decodeResource(resources, R.mipmap.ic_logo))
        if (mBitmap == null) return
        iv_card.setImageBitmap(mBitmap)
    }


    override fun onLongClick(v: View?): Boolean {
        showDialog()
        return true
    }

    private fun showDialog() {
        val dialog = MyBottomSheetDialog(this)
        val view = View.inflate(this, R.layout.dialog_face_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.ll_save).setOnClickListener {
            val bitmap = getScreenShot(this.rl_root!!)
            saveImageToGallery(this@FaceInviteActivity, bitmap)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share).setOnClickListener {
            //动态权限
            dialog.cancel()

            UShare.doShare(this@FaceInviteActivity, "wechat_friend", title!!, content!!, url!!, picture!!, 1)
        }
        dialog.show()
    }

    private fun getScreenShot(view: View): Bitmap {
        var view = view
        view = window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        return view.drawingCache
    }


    private fun saveImageToGallery(context: Context, bmp: Bitmap) {
        // 首先保存图片路径
        val appDir = File(Environment.getExternalStorageDirectory(),
                "xiaoheiqun")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        //当前时间来命名图片
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
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
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val uri = Uri.fromFile(file)
        intent.data = uri
        context.sendBroadcast(intent)

        showMsg("图片保存到本地成功")
    }


    override fun initPresenter(): Nothing? {
        return null
    }


}
