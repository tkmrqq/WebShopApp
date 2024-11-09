package com.tkmrqq.pmsapp

import android.app.Application
import com.tkmrqq.pmsapp.data.DatabaseBuilder



class App : Application() {
    val database by lazy { DatabaseBuilder.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
    }
}
