package com.example.reproductorprueba.galeria

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.example.reproductorprueba.R
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Fotos(val url: String?) : Parcelable {


    constructor(parcel: Parcel) : this(parcel.readString())

    companion object : Parceler<Fotos> {

        override fun Fotos.write(parcel: Parcel, flags: Int) {
            parcel.writeString(url)
        }

        override fun create(parcel: Parcel): Fotos {
            return Fotos(parcel)

        }

        fun getFotos(): Array<Fotos> {

            var url1:String = getURLForResource(R.drawable.mus)
            var url2:String = getURLForResource(R.drawable.music)
            var url3:String = getURLForResource(R.drawable.music2)
            var url4:String = getURLForResource(R.drawable.musica)
            var url5:String = getURLForResource(R.drawable.musica2)
            var url6:String = getURLForResource(R.drawable.musica3)
            var url7:String = getURLForResource(R.drawable.musica4)
            var url8:String = getURLForResource(R.drawable.musica5)

            return arrayOf(
                Fotos(url1),
                Fotos(url2),
                Fotos(url3),
                Fotos(url4),
                Fotos(url5),
                Fotos(url6),
                Fotos(url7),
                Fotos(url8)
            )
        }

        fun getURLForResource(resourceId: Int): String {
            return Uri.parse(
                "android.resource://" + (R::class.java.getPackage()?.getName() ) + "/" + resourceId
            ).toString()
        }

    }
}