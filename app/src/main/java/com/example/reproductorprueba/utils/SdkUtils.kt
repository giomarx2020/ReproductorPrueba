package com.example.reproductorprueba.utils

import android.os.Build
import androidx.annotation.IntRange

fun versionFrom(@IntRange(from = 1, to = 29) versionCode: Int): Boolean {
    return Build.VERSION.SDK_INT >= versionCode
}

fun versionUntil(@IntRange(from = 1, to = 29) versionCode: Int): Boolean {
    return Build.VERSION.SDK_INT <= versionCode
}