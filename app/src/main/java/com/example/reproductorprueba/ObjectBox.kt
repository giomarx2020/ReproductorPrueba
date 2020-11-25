package com.example.reproductorprueba

import android.content.Context
import com.example.reproductorprueba.data.db.MyObjectBox
import io.objectbox.BoxStore
import javax.inject.Singleton

@Singleton
object ObjectBox{
    lateinit var boxStore: BoxStore

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun get(): BoxStore? {
        return boxStore
    }
}
