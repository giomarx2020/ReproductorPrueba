package com.example.reproductorprueba.utils


import android.app.Activity
import android.app.Service
import com.example.reproductorprueba.App
import com.example.reproductorprueba.di.AppComponent

internal val Activity.injector: AppComponent
    get() = (applicationContext as App).appComponent

internal val Service.injector: AppComponent
    get() = (applicationContext as App).appComponent