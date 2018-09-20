package com.run.common.helper

import android.content.Context
import com.run.common.utils.USharePreference

/**
 * SharedPreferenceHelper配置文件保存
 */
object SharedPreferenceHelper {
    /**
     * 是否开启过引导页面
     */
    fun checkHasOpenGuide(context: Context): Boolean {
        return USharePreference.get(context, "open_guide", false) as Boolean
    }
    /**
     * 设置开启过Guide
     */
    fun openGuide(context: Context) {
        USharePreference.put(context, "open_guide", true)
    }
}