package com.run.common.utils

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 网络请求工具类
 */


object URetrofit {
    private val TAG = URetrofit.javaClass.name

    private const val READ_TIME_OUT = 10//连接时间
    private const val CONNECT_TIME_OUT = 60//链接超时
    private var mRetrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    fun getInstance(baseUrl: String, context: Context): Retrofit? {
        if (okHttpClient == null) {
            //开启Log
            val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                ULog.d(TAG, "message : $it")
            })
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(logInterceptor)
                    .addInterceptor { chain ->
                        val request = chain.request()
                                .newBuilder()
                                .removeHeader("User-Agent")//移除旧的
                                .addHeader("User-Agent", USystem.getUserAgent(context))
                                .build()
                        chain.proceed(request)
                    }
                    .build()
        }
        mRetrofit = Retrofit.Builder()
                .client(okHttpClient!!)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        return mRetrofit
    }




}
