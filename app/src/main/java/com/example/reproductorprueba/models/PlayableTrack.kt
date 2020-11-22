package com.example.reproductorprueba.models

import android.net.Uri

data class PlayableTrack(
    val mediaId: String?,
    val title: String?,
    val artist: String,
    val icon: Uri?,
    val isPlaying: Boolean = false
)