package com.example.reproductorprueba.mappers

import androidx.core.net.toUri
import com.example.reproductorprueba.models.MediaId
import com.example.reproductorprueba.models.PlayableTrack
import com.example.reproductorprueba.models.Track

internal fun Track.toPlayableTrack(): PlayableTrack {
    val mediaId = "${MediaId.TRACK}-${id}"

    return PlayableTrack(
        mediaId,
        title,
        artist,
        albumArt.toUri()
    )
}