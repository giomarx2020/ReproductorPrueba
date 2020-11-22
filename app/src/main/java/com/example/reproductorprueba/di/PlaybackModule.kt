package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.playback.player.TrackPlayer
import com.example.reproductorprueba.playback.session.MediaLoader
import com.example.reproductorprueba.repositories.*
import com.example.reproductorprueba.repositories.AlbumRepository
import com.example.reproductorprueba.repositories.ArtistRepository
import com.example.reproductorprueba.repositories.GenreRepository
import com.example.reproductorprueba.repositories.PlaylistRepository
import com.example.reproductorprueba.repositories.TrackRepository
import dagger.Module
import dagger.Provides

@Module
internal class PlaybackModule {

    @Provides
    fun providesMediaLoader(
        context: Context,
        albumRepository: AlbumRepository,
        artistRepository: ArtistRepository,
        genreRepository: GenreRepository,
        trackRepository: TrackRepository,
        playlistRepository: PlaylistRepository
    ): MediaLoader {
        return MediaLoader(
            context,
            albumRepository,
            artistRepository,
            genreRepository,
            trackRepository,
            playlistRepository
        )
    }

    @Provides
    fun providesTrackPlayer(context: Context, trackRepository: TrackRepository): TrackPlayer {
        return TrackPlayer(
            context,
            trackRepository
        )
    }

}