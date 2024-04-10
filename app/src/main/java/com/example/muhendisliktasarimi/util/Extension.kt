package com.example.muhendisliktasarimi.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable


fun String.capitalizeFirstLetter(): String {
    return if (isNotEmpty()) {
        this[0].toUpperCase() + substring(1)
    } else {
        this
    }
}


