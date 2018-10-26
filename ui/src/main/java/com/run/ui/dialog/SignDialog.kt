package com.run.ui.dialog

import android.content.Context
import com.run.common.R
import com.run.common.dialog.BaseDialogFragment


/**
 * 清楚缓存对话框
 */
class SignDialog : BaseDialogFragment() {
    companion object {
        fun newInstance(context: Context, msg: String): SignDialog {
            var dialog = SignDialog()
            dialog.arguments = getBudle("",
                    msg,
                    "去转发",
                    context.resources.getString(R.string.cancle),
                    true)
            return dialog
        }
    }
}