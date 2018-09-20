package com.run.common.utils

import android.util.Log

object ULog {

    /*
     *默认打印的TAG
     */
    private val TAG = ULog.javaClass.name
    /**
     * 是否打印日志
     */
    private var debug: Boolean = true

    /*
     *初始化打印日志
     */
    fun init(debug: Boolean) {
        this.debug = debug
    }


    /**
     * 打印错误日志
     */
    fun e(obj: Any) {
        e(TAG, obj)
    }

    fun e(tag: String, obj: Any) {
        log(E, tag, obj)
    }

    /*
     *打印普通的日志
     */
    fun d(obj: Any) {
        e(TAG, obj)
    }

    fun d(tag: String, obj: Any) {
        log(D, tag, obj)
    }


    /**
     * 日志的打印级别
     */
    private const val D = 1
    private const val E = 2

    /**
     * 执行打印方法
     */
    private fun log(type: Int, tagStr: String, obj: Any) {
        if (!debug) return

        val startTrace = Thread.currentThread().stackTrace
        val index = 4
        val className: String = startTrace[index].fileName
        var methodName: String = startTrace[index].className
        val lineNumber: Int = startTrace[index].lineNumber

        val msg: String?
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ")
        msg = obj.toString()
        stringBuilder.append(msg)

        val logStr = stringBuilder.toString()
        when (type) {
            D -> Log.d(tagStr, logStr)
            E -> Log.e(tagStr, logStr)
        }
    }

}