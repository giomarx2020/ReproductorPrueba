package com.example.reproductorprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.example.reproductorprueba.ui.DashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    setVisible(true)
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
                else -> super.onOptionsItemSelected(it)
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }



}


