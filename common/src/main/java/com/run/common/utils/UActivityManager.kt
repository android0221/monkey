package com.run.common.utils

import android.app.Activity
import java.util.*


/**
 * Activity相关管理的工具类
 */
object UActivityManager {

    val TAG = "UActivityMananger"
    /**
     * stack是栈
     *  继承于Vector(http://www.cnblogs.com/skywang12345/p/3308833.html)
     *  特点：1>先进后出
     *       2>通过数组来实现的而非链表
     *
     */
    private var mStack: Stack<Activity>? = null


    /**
     * 初始化Stack栈
     */
    private fun initStack() {
        if (mStack == null) {
            mStack = Stack()
        }
    }

    /**
     * 将activity添加到管理栈队列中
     */
    fun addActivity(act: Activity) {
        initStack()
        mStack!!.add(act)
    }

    /**
     * 移除Activity
     */
    fun removeActivity(act: Activity) {
        mStack!!.remove(act)
    }

    /**
     * 获取当前最前面的actvity(栈中最后一个加入的)
     */
    fun currentActivity(): Activity {
        return mStack!!.lastElement()

    }

    /**
     *关闭所有的activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = mStack!!.size
        while (i < size) {
            if (null != mStack!![i]) {
                val act: Activity? = mStack!![i]
                if (!act!!.isFinishing) {
                    act.finish()
                }
            }
            i++
        }
        mStack!!.clear()
    }

    /**
     * 退出应用程序
     * 这里关闭的是所有的Activity，没有关闭Activity之外的其他组件;
     * android.os.Process.killProcess(android.os.Process.myPid())
     * 杀死进程关闭了整个应用的所有资源，有时候是不合理的，通常是用
     * 堆栈管理Activity;System.exit(0)杀死了整个进程，这时候活动所占的
     * 资源也会被释放,它会执行所有通过Runtime.addShutdownHook注册的shutdown hooks.
     * 它能有效的释放JVM之外的资源,执行清除任务，运行相关的finalizer方法终结对象，
     * 而finish只是退出了Activity。
     */
    fun exit() {
        try {
            finishAllActivity()
            //DalvikVM的本地方法
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
            //这些方法如果是放到主Activity就可以退出应用，如果不是主Activity
            //就是退出当前的Activity
        } catch (e: Exception) {
            ULog.e(TAG, e)
        }
    }
}