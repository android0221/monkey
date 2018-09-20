@file:Suppress("DEPRECATION")

package com.run.common.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager

import com.run.common.R


/**
 * 对话框
 */
object DialogHelper {

    var mDialog: Dialog? = null

    @JvmOverloads
    fun showDialog(context: Context, contentView: View?, cancleable: Boolean = true) {
        if (contentView == null) return
        if (mDialog == null) {
            mDialog = Dialog(context, R.style.NormalDialogStyle)
        }
        mDialog!!.setContentView(contentView)
        mDialog!!.setCancelable(cancleable)
        mDialog!!.setCanceledOnTouchOutside(cancleable)
        mDialog!!.show()
        val win = mDialog!!.window
        win!!.decorView.setPadding(100, 0, 100, 0)
        val lp = win.attributes
        lp.width = WindowManager.LayoutParams.FILL_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        win.attributes = lp
    }


    fun closeDialog() {
        if (mDialog != null) {
            mDialog!!.cancel()
            mDialog = null
        }
    }


}
