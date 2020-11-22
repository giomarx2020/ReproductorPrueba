package com.example.reproductorprueba.galeria

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.example.reproductorprueba.R
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class FotoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUNSET_PHOTO = "FotoActivity.EXTRA_SUNSET_PHOTO"
    }

    private lateinit var imageView: ImageView
    private lateinit var fotos: Fotos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

        fotos = intent.getParcelableExtra(EXTRA_SUNSET_PHOTO)
        imageView = findViewById(R.id.image)

    }

    override fun onStart() {
        super.onStart()

        Picasso.get()
            .load(fotos.url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_error_24)
            .fit()
            .into(imageView, object : Callback {

                override fun onSuccess() {
                    val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                    onPalette(Palette.from(bitmap).generate())
                }

                override fun onError(e: Exception?) {
                }
            })


         }
    fun onPalette(palette: Palette?) {
        if (null != palette) {
            val parent = imageView.parent.parent as ViewGroup
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY))
        }
    }

}

