package com.run.common

import android.app.Application
import android.content.Context


open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        var context: Context? = null
            private set
    }

}
