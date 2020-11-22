package com.example.reproductorprueba

import android.app.Application
import com.example.reproductorprueba.di.AppComponent
import com.example.reproductorprueba.di.DaggerAppComponent
import timber.log.Timber

internal class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }

}