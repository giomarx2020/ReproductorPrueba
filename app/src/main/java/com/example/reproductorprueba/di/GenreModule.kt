package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.repositories.GenreRepository
import dagger.Module
import dagger.Provides

@Module
internal class GenreModule {

    @Provides
    fun providesGenreProvider(applicationContext: Context): GenreRepository {
        return GenreRepository(
            applicationContext
        )
    }
}