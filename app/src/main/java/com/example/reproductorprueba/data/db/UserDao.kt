package com.example.reproductorprueba.data.db

import io.objectbox.BoxStore

class UserDao(boxStore: BoxStore?, java: Class<UsuarioModel>) : IUser {
    fun UserDao(boxStore: BoxStore?, userClass: Class<UsuarioModel?>?) {
        super(boxStore, userClass)
    }

    override fun exist(id: Long): Boolean? {

    }

    override fun saveIfNotExist(user: UsuarioModel?): Boolean{

    }

    override fun findByTec(id: Long): UsuarioModel? {

        return BoxStore.query().equal(UsuarioModel_.id, id).build().findUnique()
    }


}