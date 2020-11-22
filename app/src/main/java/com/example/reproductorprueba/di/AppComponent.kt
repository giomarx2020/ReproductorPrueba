package com.example.reproductorprueba.di

import android.content.Context
import com.example.reproductorprueba.base.BaseActivity
import com.example.reproductorprueba.playback.session.MediaService
import com.example.reproductorprueba.ui.DashboardActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,

        PlaybackModule::class,
        DataModule::class,

        //Media Categories
        AlbumModule::class,
        ArtistModule::class,
        PlaylistModule::class,
        TrackModule::class,
        GenreModule::class
    ]
)
internal interface AppComponent {

    fun inject(baseActivity: BaseActivity)

    fun inject(zikkMediaService: MediaService)

    fun inject(dashboardActivity: DashboardActivity)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent

    }
}