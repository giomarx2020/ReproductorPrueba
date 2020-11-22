package com.example.reproductorprueba.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class UsuarioModel(@Id var id: Long = 0,
                        var nombre: String = "",
                        var apellido: String = "",
                        var email: String = "",
                        var Biografia: String = "",
                        var url: String = "") {

}