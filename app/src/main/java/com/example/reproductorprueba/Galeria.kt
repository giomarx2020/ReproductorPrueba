package com.example.reproductorprueba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reproductorprueba.galeria.FotoActivity
import com.example.reproductorprueba.galeria.Fotos
import com.example.reproductorprueba.ui.DashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import java.io.File

class Galeria : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tvLabel: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)

        bottomNav = findViewById(R.id.bottomNav)


        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    val home = Intent(this, MainActivity::class.java)
                    startActivity(home)

                    true
                }
                R.id.menu_reproductor -> {

                    val reproductor = Intent(this, DashboardActivity::class.java)
                    startActivity(reproductor)

                    true
                }
                R.id.menu_galeria -> {
                    val galeria = Intent(this, Galeria::class.java)
                    startActivity(galeria)

                    true
                }
                R.id.menu_perfil -> {
                    val perfil = Intent(this, Perfil::class.java)
                    startActivity(perfil)

                    true
                }
                else -> false
            }


        }


        val layoutManager = GridLayoutManager(this, 2)
            recyclerView = this.findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        imageGalleryAdapter = ImageGalleryAdapter(this, Fotos.getFotos())
    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = imageGalleryAdapter

    }

        inner class ImageGalleryAdapter(val context: Context, val sunsetPhotos: Array<Fotos>)
            : RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

             override fun onCreateViewHolder(
                 parent: ViewGroup,
                 viewType: Int
             ): ImageGalleryAdapter.MyViewHolder {
                 val context = parent.context
                 val inflater = LayoutInflater.from(context)
                 val photoView = inflater.inflate(R.layout.item_image, parent, false)
                 return MyViewHolder(photoView)
             }

            override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
                val sunsetPhotos = sunsetPhotos[position]
                val imageView = holder.photoImageView

                Picasso.get()
                    .load(sunsetPhotos.url)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .into(imageView)



            }

             override fun getItemCount(): Int {
                 return sunsetPhotos.size
             }



             inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                 View.OnClickListener {

                 var photoImageView: ImageView = itemView.findViewById(R.id.iv_photo)

                 init {
                     itemView.setOnClickListener(this)
                 }

                 override fun onClick(view: View) {
                     val position = adapterPosition
                     if (position != RecyclerView.NO_POSITION) {
                         val sunsetPhoto = sunsetPhotos[position]
                         val intent = Intent(context, FotoActivity::class.java).apply {
                             putExtra(FotoActivity.EXTRA_SUNSET_PHOTO, sunsetPhoto)
                         }
                         startActivity(intent)
                     }
                 }


             }
         }

}