package com.example.reproductorprueba.playback.session

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN
import androidx.core.net.toUri
import com.example.reproductorprueba.data.LastPlayedTrackPreference
import com.example.reproductorprueba.models.MediaId
import com.example.reproductorprueba.models.Track
import com.example.reproductorprueba.notification.PlaybackNotificationBuilder
import com.example.reproductorprueba.playback.BecomingNoisyReceiver
import com.example.reproductorprueba.playback.player.TrackPlayer
import com.example.reproductorprueba.utils.Constants.PLAYBACK_NOTIFICATION_ID
import com.example.reproductorprueba.utils.getAlbumArtBitmap
import com.example.reproductorprueba.utils.versionFrom
import timber.log.Timber

internal class MediaSessionCallback(
    private val serviceContext: Service,
    private val playbackNotificationBuilder: PlaybackNotificationBuilder,
    private val mediaSessionCompat: MediaSessionCompat,
    private val audioManager: AudioManager,
    private val becomingNoisyReceiver: BecomingNoisyReceiver,
    private val playbackStateBuilder: PlaybackStateCompat.Builder,
    private val metadataCompatBuilder: MediaMetadataCompat.Builder,
    private val trackPlayer: TrackPlayer,
    lastPlayedTrackPreference: LastPlayedTrackPreference
) : MediaSessionCompat.Callback(), AudioManager.OnAudioFocusChangeListener {

    private var audioFocusRequest: AudioFocusRequest? = null

    init {
        setPlaybackState(
            playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_PAUSED,
                PLAYBACK_POSITION_UNKNOWN,
                0.0F
            )
        )
        val track =
            trackPlayer.getTrackInformationFromTrackId(lastPlayedTrackPreference.lastPlayedTrackId)
        track?.run {
            setSessionMetadata(this)
            mediaSessionCompat.controller
                .transportControls
                .prepareFromMediaId("${MediaId.TRACK}-${this.id}", null)
        }
    }

    override fun onPlayFromMediaId(mediaId: String, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
        val request = initAudioFocus()
        if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Timber.d("Playing from media id $mediaId")
            startPlayback(mediaId)
        }
    }

    override fun onPlay() {
        super.onPlay()
        val request = initAudioFocus()
        if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            resumePlayback()
        }
    }

    override fun onPause() {
        super.onPause()
        with(serviceContext) {
            trackPlayer.pause()
            unregisterReceiver(becomingNoisyReceiver)
            val state = playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_PAUSED,
                mediaSessionCompat.controller.playbackState.position,
                0.0F
            )
            setPlaybackState(state)
            updateMediaNotification()
            stopForeground(false)
        }
    }

    override fun onStop() {
        super.onStop()
        releaseAudioFocus()
        with(serviceContext) {
            trackPlayer.stop()
            val state = playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_STOPPED,
                mediaSessionCompat.controller.playbackState.position,
                0.0F
            )
            setPlaybackState(state)
            stopForeground(true)
            stopSelf()
        }
    }

    override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPrepareFromMediaId(mediaId, extras)
        with(trackPlayer) {
            reset()
            setDataSourceFromMediaId(serviceContext, mediaId!!)
            prepare(delayStart = true)
        }
        trackPlayer.getTrackInformationFromMediaId(mediaId!!)?.let { track ->
            setSessionMetadata(track)
        }
        val state = playbackStateBuilder.setState(
            PlaybackStateCompat.STATE_PAUSED,
            PLAYBACK_POSITION_UNKNOWN,
            0.0F
        )
        setPlaybackState(state)
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                trackPlayer.pause()
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                releaseAudioFocus()
                trackPlayer.pause()
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                trackPlayer.start()
            }
        }
    }

    private fun startPlayback(mediaId: String) {
        Timber.i("Starting playback")
        with(serviceContext) {
            startService(Intent(this, MediaService::class.java))
            with(trackPlayer) {
                reset()
                setDataSourceFromMediaId(serviceContext, mediaId)
                prepare()
            }
            trackPlayer.getTrackInformationFromMediaId(mediaId)?.let { track ->
                setSessionMetadata(track)
            }
            val state = playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_PLAYING,
                mediaSessionCompat.controller.playbackState.position,
                0.0F
            )
            setPlaybackState(state)
            registerNoisyReceiver()
            createMediaNotification()
        }
    }

    private fun resumePlayback() {
        Timber.i("Resuming playback")
        with(serviceContext) {
            startService(Intent(this, MediaService::class.java))
            trackPlayer.start()
            val state = playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_PLAYING,
                mediaSessionCompat.controller.playbackState.position,
                0.0F
            )
            setPlaybackState(state)
            registerNoisyReceiver()
            createMediaNotification()
        }
    }

    private fun setPlaybackState(state: PlaybackStateCompat.Builder) {
        mediaSessionCompat.setPlaybackState(state.build())
    }

    private fun Service.registerNoisyReceiver() {
        registerReceiver(becomingNoisyReceiver, intentFilter)
    }

    private fun Service.createMediaNotification() {
        val notification = playbackNotificationBuilder.build()
        startForeground(PLAYBACK_NOTIFICATION_ID, notification)
    }

    private fun updateMediaNotification() {
        playbackNotificationBuilder.build()
    }

    private fun initAudioFocus(): Int {
        return if (versionFrom(Build.VERSION_CODES.O)) {
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(this)
                .setAudioAttributes(AudioAttributes.Builder().run {
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                .build()
            audioManager.requestAudioFocus(audioFocusRequest!!)
        } else {
            audioManager.requestAudioFocus(
                this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
            )
        }
    }

    private fun setSessionMetadata(track: Track) {
        val trackMetadata = metadataCompatBuilder.apply {
            putString(METADATA_KEY_ALBUM, track.album)
            putString(METADATA_KEY_ARTIST, track.artist)
            putString(METADATA_KEY_TITLE, track.title)
            putString(METADATA_KEY_ALBUM_ART_URI, track.albumArt)
            putBitmap(
                METADATA_KEY_ALBUM_ART,
                getAlbumArtBitmap(serviceContext, track.albumArt.toUri())
            )
            putString(METADATA_KEY_MEDIA_ID, track.id.toString())
            putLong(METADATA_KEY_DURATION, track.duration.toLong())
        }
        mediaSessionCompat.setMetadata(trackMetadata.build())
    }

    private fun releaseAudioFocus() {
        if (versionFrom(Build.VERSION_CODES.O)) {
            audioFocusRequest?.run {
                audioManager.abandonAudioFocusRequest(this)
            }
        } else {
            audioManager.abandonAudioFocus(this)
        }
    }

    companion object {
        private val intentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
    }

}