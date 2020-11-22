package com.example.reproductorprueba

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.reproductorprueba.ui.DashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class Reproductor : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var cancion: MutableList<Int> = mutableListOf(R.raw.skrillex)

    private lateinit var buttonPlay: FloatingActionButton
    private lateinit var buttonPause: FloatingActionButton
    private lateinit var buttonStop: FloatingActionButton
    private lateinit var seekBar: SeekBar
    private lateinit var bottomNav: BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor)

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)
        seekBar = findViewById(R.id.seekBar)

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

        controlSound(cancion[0])

        }

    private fun controlSound(id: Int){

        buttonPlay.setOnClickListener {
            if(mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                iniciarSeekBar()
            }

            mp?.start()
            Log.d("Reproductor", "Duration: ${mp!!.duration/1000} seconds")
        }

        buttonPause.setOnClickListener{
            if(mp != null) mp?.pause()
        }

        buttonStop.setOnClickListener{
            if(mp != null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }

        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {

                if(fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun iniciarSeekBar(){
        seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try{
                    seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    seekBar.progress = 0
                }
            }
        },0)
    }

    private fun setContent(content: String) {
        setTitle(content)

    }



}