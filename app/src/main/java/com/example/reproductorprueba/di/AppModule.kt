package com.example.reproductorprueba.di

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import com.example.reproductorprueba.notification.NotificationsChannelManager
import com.example.reproductorprueba.utils.Constants
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun providesAudioManager(context: Context): AudioManager {
        return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    @Provides
    fun providesSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun providesNotificationChannelsManager(
        context: Context,
        notificationManager: NotificationManager
    ): NotificationsChannelManager {
        return NotificationsChannelManager(context, notificationManager)
    }
}