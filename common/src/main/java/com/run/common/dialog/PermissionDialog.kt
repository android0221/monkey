package com.run.common.dialog

import android.content.Context
import com.run.common.R


/**
 * 权限请求兑换框
 */
class PermissionDialog : BaseDialogFragment() {


    companion object {
        fun newInstance(context: Context): PermissionDialog {
            var dialog = PermissionDialog()
            dialog.arguments = getBudle("",
                    context.resources.getString(R.string.request_permisson_hint),
                    context.resources.getString(R.string.positive),
                    context.resources.getString(R.string.cancle),
                    false)
            return dialog
        }
    }

}