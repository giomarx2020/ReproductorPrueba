package com.example.reproductorprueba.data.db

import com.example.reproductorprueba.ObjectBox
import io.objectbox.BoxStore
import javax.inject.Inject

class AppDbHelper : DBHelper  {

    private var boxStore: BoxStore? = null

    private val userDao: IUser? = null

    @Inject
    fun AppDbHelper(objectBox: ObjectBox) {
        boxStore = objectBox.get()
    }

    fun getUserDao(): IUser? {
        return if (userDao == null) UserDao(boxStore, UsuarioModel::class.java) else userDao
    }

}