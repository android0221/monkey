package com.run.common.dialog

import android.content.Context
import com.run.common.R


/**
 * 清楚缓存对话框
 */
class ClearCacherDialog : BaseDialogFragment() {
    companion object {
        fun newInstance(context: Context): ClearCacherDialog {
            var dialog = ClearCacherDialog()
            dialog.arguments = getBudle("",
                    context.resources.getString(R.string.clear_cacher),
                    context.resources.getString(R.string.positive),
                    context.resources.getString(R.string.cancle),
                    true)
            return dialog
        }
    }
}