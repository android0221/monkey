package com.run.common.base

/**
 * 页面回调的基类
 */
interface BaseMvpView {
    /**
     * 显示正在加载view
     */
    fun showLoading()

    /**
     * 关闭正在加载view
     */
    fun hideLoading()


    /**
     * @param msg 显示错误信息
     */
    fun showErr(errorType: Int, msg: String)


    fun showMsg(msg: String?)


}
