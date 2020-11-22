package com.example.reproductorprueba.di

import android.content.SharedPreferences
import com.example.reproductorprueba.data.LastPlayedTrackPreference
import dagger.Module
import dagger.Provides

@Module
internal class DataModule {

    @Provides
    fun providesLastPlayedTrackPreference(sharedPreferences: SharedPreferences):LastPlayedTrackPreference{
        return LastPlayedTrackPreference(sharedPreferences)
    }
}