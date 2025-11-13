package com.example.swemo

import android.app.Application
import com.example.datastore.SystemPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SwemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SystemPreferences.init(context = this)
    }
}