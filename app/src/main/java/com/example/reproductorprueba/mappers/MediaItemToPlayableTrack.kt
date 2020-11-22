package com.example.reproductorprueba.mappers

import android.support.v4.media.MediaBrowserCompat
import com.example.reproductorprueba.models.PlayableTrack

fun MediaBrowserCompat.MediaItem.toTrack(): PlayableTrack {
    return PlayableTrack(
        this.mediaId,
        this.description.title.toString(),
        this.description.subtitle.toString(),
        this.description.iconUri
    )
}
