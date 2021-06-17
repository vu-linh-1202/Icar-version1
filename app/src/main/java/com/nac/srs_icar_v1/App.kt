package com.nac.srs_icar_v1

import android.app.Application

class App : Application() {
    var storage: Storage? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        storage = Storage()
    }

    companion object {
        @JvmStatic
        var instance: App? = null
            private set
    }
}