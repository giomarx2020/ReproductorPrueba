package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.repositories.AlbumRepository
import com.example.reproductorprueba.repositories.TrackRepository
import dagger.Module
import dagger.Provides

@Module
internal class TrackModule {

    @Provides
    fun providesTrackProvider(context: Context, albumRepository: AlbumRepository): TrackRepository {
        return TrackRepository(
            context,
            albumRepository
        )
    }

}