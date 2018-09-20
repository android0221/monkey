package com.run.login.modle


import com.run.config.modle.BaseModle

/**
 * Created by xiaor on 2018/4/2.
 */

class VersionModle : BaseModle() {
    val url: String? = null//更新的url
    val versions: Int = 0//版本号
    val type: Int = 0//更新方式 0不用强制更新，1要强制更新
    val describe: String? = null//更新内容
}
