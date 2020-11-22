package com.example.reproductorprueba.ui

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.reproductorprueba.R
import com.example.reproductorprueba.models.PlayableTrack
import com.example.reproductorprueba.utils.invisible
import com.example.reproductorprueba.utils.show
import com.example.reproductorprueba.utils.versionFrom

internal class TracksAdapter(val onClick: (String?, Int) -> Unit) :
    RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    private lateinit var mediaItems: MutableList<PlayableTrack>

    fun setList(mediaItem: MutableList<PlayableTrack>) {
        mediaItems = mediaItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(mediaItems[position])
    }

    override fun onBindViewHolder(
        holder: TrackViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.setIsPlaying(payloads[0] as Boolean)
    }


    inner class TrackViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val nowPlayingImageView: ImageView =
            view.findViewById(R.id.track_now_playing_image_view)

        lateinit var animatedNowPlayingDrawable: AnimatedVectorDrawable

        fun bind(mediaItem: PlayableTrack) {
            with(view) {
                findViewById<ImageView>(R.id.track_art_image_view).apply {
                    this.load(mediaItem.icon)
                    contentDescription = "${mediaItem.title} Album Art"
                }
                findViewById<TextView>(R.id.track_title_text_view).apply {
                    text = mediaItem.title
                }
                findViewById<TextView>(R.id.track_artist_text_view).apply {
                    text = mediaItem.artist
                }

                nowPlayingImageView.apply {
                    setBackgroundResource(R.drawable.now_playing_anim)
                    animatedNowPlayingDrawable = background as AnimatedVectorDrawable
                    if (versionFrom(Build.VERSION_CODES.M))
                        animatedNowPlayingDrawable.registerAnimationCallback(object :
                            Animatable2.AnimationCallback() {
                            override fun onAnimationEnd(drawable: Drawable?) {
                                super.onAnimationEnd(drawable)
                                animatedNowPlayingDrawable.start()
                            }
                        })
                }

                setNowPlayingViewVisibility(mediaItem.isPlaying, nowPlayingImageView)

                setOnClickListener {
                    onClick(mediaItem.mediaId, adapterPosition)
                }
            }
        }

        fun setIsPlaying(isPlaying: Boolean) {
            setNowPlayingViewVisibility(isPlaying, nowPlayingImageView)
        }

        private fun setNowPlayingViewVisibility(isPlaying: Boolean, showPlaying: ImageView) {
            if (isPlaying) {
                showPlaying.show()
                animatedNowPlayingDrawable.start()
            } else {
                showPlaying.invisible()
                animatedNowPlayingDrawable.stop()
            }
        }
    }

    fun updateIsPlaying(position: Int, payload: Any) {
        notifyItemChanged(position, payload)
    }

    override fun getItemCount(): Int {
        return if (::mediaItems.isInitialized) mediaItems.size else 0
    }


}