package com.example.reproductorprueba.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.reproductorprueba.notification.NotificationsChannelManager
import com.example.reproductorprueba.notification.NotificationsChannelManager.Companion.PLAYBACK_CHANNEL_ID
import com.example.reproductorprueba.utils.injector
import com.example.reproductorprueba.utils.versionFrom
import javax.inject.Inject

open class BaseActivity: AppCompatActivity() {

    @Inject
            lateinit var notificationsChannelManager: NotificationsChannelManager


    override fun onCreate(savedInstanceState: Bundle?) {
        matchStatusBarWithBackground()
        injector.inject(this)
        super.onCreate(savedInstanceState)
        initNotificationChannel()
    }

    private fun matchStatusBarWithBackground() {
        if (versionFrom(Build.VERSION_CODES.M)) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = getColor(android.R.color.background_light)
        }
    }

    private fun initNotificationChannel() {
        with(notificationsChannelManager) {
            if (versionFrom(Build.VERSION_CODES.O) && !hasChannel(PLAYBACK_CHANNEL_ID))
                createNotificationChannel(PLAYBACK_CHANNEL_ID)
        }
    }
}