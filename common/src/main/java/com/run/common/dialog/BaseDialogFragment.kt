package com.run.common.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.run.common.utils.ULog

/**
 * DialogFragment的基类
 */
@Suppress("JAVA_CLASS_ON_COMPANION")
abstract class BaseDialogFragment : DialogFragment() {

    companion object {

        private val TAG = BaseDialogFragment.javaClass.name

        private const val DIALOG_TITLE = "dialog_title"
        private const val DIALOG_MESSAGE = "dialog_message"
        private const val DIALOG_POSITIVE = "dialog_positive"
        private const val DIALOG_NEGATIVE = "dialog_negative"
        private const val DIALOG_CANCELABLE = "dialog_cancelable"

        fun getBudle(title: String?, msg: String?, positive: String?, negative: String?, cancelable: Boolean): Bundle {
            var bundle = Bundle()
            bundle.putString(DIALOG_TITLE, title)
            bundle.putString(DIALOG_MESSAGE, msg)
            bundle.putString(DIALOG_POSITIVE, positive)
            bundle.putString(DIALOG_NEGATIVE, negative)
            bundle.putBoolean(DIALOG_CANCELABLE, cancelable)
            return bundle
        }
    }


    /**
     * 会话框回调
     */
    private var mDialogCallBack: DialogCallBack? = null

    /**
     * 初始化兑换框
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        ULog.d(TAG, "onCreateDialog()")
        val builder = AlertDialog.Builder(activity!!)
        //设置对话框标题
        val title = arguments!!.getString(DIALOG_TITLE)
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        //设置对话框内容
        val msg = arguments!!.getString(DIALOG_MESSAGE)
        if (!TextUtils.isEmpty(msg)) { builder.setMessage(msg) }

        //肯定对话框
        val positive = arguments!!.getString(DIALOG_POSITIVE)
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive) { _, _ ->
                if (mDialogCallBack != null) {
                    mDialogCallBack!!.onNext()
                }
                dismiss()
            }
        }
        //取消对话框
        val negative = arguments!!.getString(DIALOG_NEGATIVE)
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative) { _, _ ->
                if (mDialogCallBack != null) {
                    mDialogCallBack!!.cancle()
                }
                dismiss()
            }
        }

        //设置对话点击外部能否取消
        builder.setCancelable(arguments!!.getBoolean(DIALOG_CANCELABLE))
        return builder.create()
    }


    /**
     * 显示对话框
     */
    fun show(act: AppCompatActivity, callBack: DialogCallBack) {
        this.mDialogCallBack = callBack
        super.show(act.supportFragmentManager, "")
    }

}
