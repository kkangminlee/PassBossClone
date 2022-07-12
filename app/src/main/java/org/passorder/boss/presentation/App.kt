package org.passorder.boss.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.passorder.boss.FlipperInitializer

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        FlipperInitializer.init(this)
    }
}