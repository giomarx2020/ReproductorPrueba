package com.example.reproductorprueba.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.media.session.PlaybackState.*
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.example.reproductorprueba.R
import com.example.reproductorprueba.notification.NotificationsChannelManager.Companion.PLAYBACK_CHANNEL_ID
import com.example.reproductorprueba.utils.Constants.PLAYBACK_NOTIFICATION_ID
import com.example.reproductorprueba.utils.album
import com.example.reproductorprueba.utils.albumArt
import com.example.reproductorprueba.utils.artist
import com.example.reproductorprueba.utils.title
import javax.inject.Inject

internal class PlaybackNotificationBuilder @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val mediaSessionCompat: MediaSessionCompat
) {

    @SuppressLint("WrongConstant")
    fun build(): Notification {

        val mediaMetadata = mediaSessionCompat.controller.metadata

        val notificationBuilder = NotificationCompat.Builder(context, PLAYBACK_CHANNEL_ID)
            .apply {
                // Add the metadata for the currently playing track
                setContentTitle(mediaMetadata.title)
                setContentText(mediaMetadata.artist)
                setSubText(mediaMetadata.album)
                setLargeIcon(mediaMetadata.albumArt)

                // Enable launching the player by clicking the notification
                setContentIntent(mediaSessionCompat.controller.sessionActivity)

                setDeleteIntent(
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        ACTION_STOP
                    )
                )

                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                setSmallIcon(R.drawable.ic_music_note_black_24dp)
                color = ContextCompat.getColor(context, R.color.colorAccent)

                val playPauseRes =
                    if (mediaSessionCompat.controller.playbackState.state == STATE_PLAYING)
                        R.drawable.ic_pause_black_48dp
                    else R.drawable.ic_play_black_48dp

                addAction(
                    NotificationCompat.Action(
                        playPauseRes,
                        context.getString(R.string.playback_action_pause),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            ACTION_PLAY_PAUSE
                        )
                    )
                )

                setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.sessionToken)
                        .setShowActionsInCompactView(0)
                )
            }
        val notification = notificationBuilder.build()
        notificationManager.notify(PLAYBACK_NOTIFICATION_ID, notification)
        return notification
    }

}