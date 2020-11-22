package com.example.reproductorprueba

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.reproductorprueba.data.db.UsuarioModel
import com.example.reproductorprueba.databinding.ActivityPerfilBinding
import com.example.reproductorprueba.ui.DashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.android.synthetic.main.activity_perfil.*

class Perfil : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tvLabel: TextView

    private lateinit var borrowQuery: Query<UsuarioModel>

    private val borrowBox = ObjectBox.boxStore.boxFor(UsuarioModel::class.java)

    private  val requiredBox = ObjectBox.boxStore.boxFor (UsuarioModel :: class .java)
    lateinit  var resourceQuery: Query<UsuarioModel>
    private lateinit var subscription: DataSubscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        subscription = borrowQuery
            .subscribe()
            .on(AndroidScheduler.mainThread())
            .observer { notes -> listOf(notes) }
        subscription.cancel()

        borrowQuery = borrowBox.query()
            .build()



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


    }

}