@file:Suppress("DEPRECATION")

package com.run.common.utils

import android.content.Context
import android.view.WindowManager

/**
 * Created by xiaor on 2018/4/4.
 */

object USize {

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    fun getWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width

    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    fun getHeight(context: Context): Int {
        val wn = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wn.defaultDisplay.height
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
