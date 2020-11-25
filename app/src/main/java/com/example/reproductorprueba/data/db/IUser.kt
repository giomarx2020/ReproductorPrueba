package com.example.reproductorprueba.data.db

interface IUser{

    fun exist(id: Long): Boolean?

    fun saveIfNotExist(user: UsuarioModel?): Boolean

    fun findByTec(id: Long): UsuarioModel?
}