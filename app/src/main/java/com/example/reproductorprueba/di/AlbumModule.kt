package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.repositories.AlbumRepository
import dagger.Module
import dagger.Provides

@Module
internal class AlbumModule {

    @Provides
    fun providesAlbumProvider(applicationContext: Context): AlbumRepository {
        return AlbumRepository(
            applicationContext
        )
    }
}