package com.example.reproductorprueba.playback.player

import android.content.ContentUris
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.PowerManager
import android.provider.MediaStore
import com.example.reproductorprueba.models.Track
import com.example.reproductorprueba.repositories.TrackRepository
import com.example.reproductorprueba.utils.convertMediaIdToTrackId
import timber.log.Timber

internal class TrackPlayer(
    val context: Context,
    private val trackRepository: TrackRepository
) : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    lateinit var mediaPlayer: MediaPlayer
    private var delayStart = false

    init {
        initPlayer()
    }

    private val isInitialized: Boolean
        get() = ::mediaPlayer.isInitialized

    val currentPosition: Int
        get() {
            return if (isInitialized) mediaPlayer.currentPosition else -1
        }

    val isPlaying: Boolean
        get() {
            return if (isInitialized) mediaPlayer.isPlaying else false
        }
    val duration: Int
        get() {
            return if (isInitialized) mediaPlayer.duration else -1
        }

    private fun initPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener(this@TrackPlayer)
            setOnErrorListener(this@TrackPlayer)
        }
    }

    fun prepare(delayStart: Boolean = false) {
        Timber.i("Media Player Preparing")
        this.delayStart = delayStart
        mediaPlayer.prepareAsync()
    }

    fun start() {
        Timber.i("Media Player Starting")
        mediaPlayer.start()
    }

    fun pause() {
        Timber.i("Media Player Paused")
        mediaPlayer.pause()
    }

    fun stop() {
        Timber.i("Media Player Stopped")
        mediaPlayer.stop()
    }

    fun reset() {
        Timber.i("Media Player Reset")
        mediaPlayer.reset()
    }

    fun release() {
        Timber.i("Media Player Released")
        mediaPlayer.release()
    }

    fun setDataSourceFromMediaId(context: Context, mediaId: String) {
        val trackId = convertMediaIdToTrackId(mediaId)
        if (trackId == 0L) return
        val contentUris =
            ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackId)
        mediaPlayer.setDataSource(context, contentUris)
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        Timber.i("Media Player Prepared")
        if (!delayStart)
            start()
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        Timber.i("Media Player Error : $what")
        reset()
        return true
    }

    fun getTrackInformationFromMediaId(mediaId: String): Track? {
        val trackId = convertMediaIdToTrackId(mediaId)
        return trackRepository.loadTrackForId(trackId.toString())
    }

    fun getTrackInformationFromTrackId(trackId: Long): Track? {
        return trackRepository.loadTrackForId(trackId.toString())
    }


}