package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.repositories.ArtistRepository
import dagger.Module
import dagger.Provides

@Module
internal class ArtistModule {

    @Provides
    fun providesArtistsProvider(applicationContext: Context): ArtistRepository {
        return ArtistRepository(
            applicationContext
        )
    }
}