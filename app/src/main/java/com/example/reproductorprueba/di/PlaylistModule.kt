package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.repositories.PlaylistRepository
import dagger.Module
import dagger.Provides

@Module
internal class PlaylistModule {

    @Provides
    fun providesPlaylistProvider(applicationContext: Context): PlaylistRepository {
        return PlaylistRepository(
            applicationContext
        )
    }
}