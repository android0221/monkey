package com.run.share.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast

import java.io.File
import java.util.ArrayList


class ShareManager(private val mContext: Context) {
    private val files = ArrayList<File>()

    fun setShareImage(flag: Int, stringList: List<String>, Kdescription: String, type: Int) {
        if (!Tools.isWeixinAvilible(mContext)) {
            Toast.makeText(mContext, "您还没有安装微信", Toast.LENGTH_SHORT).show()

        } else {
            Thread(Runnable {
                try {
                    for (i in stringList.indices) {
                        val file = Tools.saveImageToSdCard(mContext, stringList[i], type)
                        if (file != null) {
                            files.add(file)
                        }
                    }
                    val intent = Intent()
                    val comp: ComponentName

                    if (flag == 0) {
                        comp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
                    } else {
                        comp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI")
                        intent.putExtra("Kdescription", Kdescription)
                    }
                    intent.component = comp
                    intent.action = Intent.ACTION_SEND_MULTIPLE
                    intent.type = "image/*"
                    val imageUris = ArrayList<Uri>()

                    for (f in files) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            imageUris.add(Uri.fromFile(f))
                        } else {
                            //修复微信在7.0崩溃的问题
                            val uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.contentResolver, f.absolutePath, "bigbang.jpg", null))
                            imageUris.add(uri)
                        }
                    }
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

    }

}
