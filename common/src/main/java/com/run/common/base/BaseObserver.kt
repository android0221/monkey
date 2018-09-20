package com.run.common.base


import com.google.gson.JsonParseException
import com.run.common.utils.ULog
import com.run.config.modle.BaseModle
import io.reactivex.observers.DisposableObserver
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

abstract class BaseObserver<T : BaseModle> : DisposableObserver<T>() {


    /**
     * 网络成功回调
     */
    override fun onNext(modle: T) {
        try {
            if (modle.status == 200 || modle.success) {
                onSuccess(modle)
            } else {
                onError(RETURN_ERROR, modle.msg)
            }
        } catch (e: Exception) {
            ULog.e(TAG, e)
            onError(CONNECT_ERROR, e.toString())
        }

    }

    /**
     * 失败处理
     */
    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> onException(BAD_NETWORK) //   HTTP错误
            is ConnectException, is UnknownHostException -> onException(CONNECT_ERROR)  //   连接错误
            is InterruptedIOException -> onException(CONNECT_TIMEOUT)  //  连接超时
            is JsonParseException, is JSONException, is ParseException -> onException(PARSE_ERROR)   //  解析错误
            else -> onError(RETURN_ERROR, e.toString()) //未知错误
        }
    }

    override fun onComplete() {

    }

    private fun onException(unknownError: Int) {
        when (unknownError) {
            CONNECT_ERROR -> onError(CONNECT_ERROR, "连接错误")
            CONNECT_TIMEOUT -> onError(CONNECT_TIMEOUT, "连接超时")
            BAD_NETWORK -> onError(BAD_NETWORK, "网络问题")
            PARSE_ERROR -> onError(PARSE_ERROR, "解析数据失败")
            else -> onError(RETURN_ERROR, "未知错误")

        }
    }

    abstract fun onSuccess(o: T)

    abstract fun onError(errorType: Int, msg: String?)

    companion object {
        private val TAG: String = BaseObserver.javaClass.name

        /**
         * 解析数据失败
         */
        const val PARSE_ERROR = 1001
        /**
         * 网络问题
         */
        const val BAD_NETWORK = 1002
        /**
         * 连接错误
         */
        const val CONNECT_ERROR = 1003
        /**
         * 连接超时
         */
        const val CONNECT_TIMEOUT = 1004

        /**
         * 返回错误
         */
        const val RETURN_ERROR = 1005
    }

}
