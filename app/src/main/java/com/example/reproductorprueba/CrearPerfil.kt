package com.example.reproductorprueba

import android.content.Intent
import android.net.Uri
import android.net.sip.SipErrorCode
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.reproductorprueba.R.id.foto
import com.example.reproductorprueba.data.db.UsuarioModel
import com.example.reproductorprueba.databinding.ActivityCrearPerfilBinding
import io.objectbox.BoxStore
import kotlinx.android.synthetic.main.activity_crear_perfil.*
import kotlinx.android.synthetic.main.activity_foto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearPerfil : AppCompatActivity() {


    private lateinit var binding: ActivityCrearPerfilBinding
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_perfil)
        binding = ActivityCrearPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObjectBox.init(this)
        imageView = findViewById(R.id.foto)



        binding.guardar.setOnClickListener{
            binding.guardar.isEnabled = false

            val nombre = binding.nombre.text?.toString()
            val apellidos = binding.apellidos.text?.toString()
            val email = binding.email.text?.toString()
            val biografia = binding.Biografia.text?.toString()

            if(nombre.isNullOrBlank()){
                binding.nombre.error = "El nombre no puede estar vacio"
                binding.guardar.isEnabled = true
                return@setOnClickListener
            }
            if(apellidos.isNullOrBlank()){
                binding.apellidos.error = "El apellido no puede estar vacio"
                binding.guardar.isEnabled = true
                return@setOnClickListener
            }

            if(email.isNullOrBlank()){
                binding.email.error = "El email no puede estar vacio"
                binding.guardar.isEnabled = true
                return@setOnClickListener
            }

            if(biografia.isNullOrBlank()){
                binding.Biografia.error = "La biografia no puede estar vacio"
                binding.guardar.isEnabled = true
                return@setOnClickListener
            }


            lifecycleScope.launch(){
                putusuario(nombre,apellidos,email,biografia)
            }

            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)


        }


    }

    private suspend fun putusuario(name: String, apellidos: String,email: String, biografia: String, ) = withContext(Dispatchers.IO) {
        val newUsuario = UsuarioModel(nombre = name, apellido = apellidos, email = email, Biografia = biografia)
        ObjectBox.boxStore.boxFor(UsuarioModel::class.java).put(newUsuario)
    }

     fun onclick(view: View){
        cargarImagen()
    }

    fun cargarImagen(){
       var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        startActivityForResult(Intent.createChooser(intent,"Seleccione la Aplicaciòn"),10)

    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
         super.onActivityResult(requestCode, resultCode, data)

         if(resultCode == RESULT_OK) run {
             var path: Uri = data?.getData()?: return
             imageView.setImageURI(path)
         }

     }

}