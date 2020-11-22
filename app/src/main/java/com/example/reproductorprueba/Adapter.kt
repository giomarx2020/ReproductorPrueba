package com.example.reproductorprueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.reproductorprueba.data.db.UsuarioModel
import kotlinx.android.synthetic.main.activity_crear_perfil.view.*
import kotlinx.android.synthetic.main.activity_perfil.view.*
import timber.log.Timber.tag

class Adapter : BaseAdapter() {

    private val dataset: MutableList<UsuarioModel> = mutableListOf()

    private class view(item: View){
        val nombre: TextView = item.findViewById(R.id.nombre)
        val apelllidos: TextView = item.findViewById(R.id.apellidos)
        val email: TextView = item.findViewById(R.id.email)
        val biografia: TextView = item.findViewById(R.id.Biografia)

    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int) {

    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view1 = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.activity_perfil, parent, false).also {
                it.tag = view(it)
            }


        return view1
    }
}