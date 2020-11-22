package com.example.reproductorprueba.utils

import android.support.v4.media.session.MediaControllerCompat
import com.example.reproductorprueba.ui.DashboardActivity


internal val DashboardActivity.mediaTranspotControls: MediaControllerCompat.TransportControls?
    get() = MediaControllerCompat.getMediaController(this).transportControls

internal val DashboardActivity.mediaControllerCompat: MediaControllerCompat?
    get() = MediaControllerCompat.getMediaController(this)

