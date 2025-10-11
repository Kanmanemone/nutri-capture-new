package com.example.nutri_capture_new

import android.app.Application
import com.example.datastore.SystemPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SystemPreferences.init(context = this)
    }
}