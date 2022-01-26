package com.example.chat.extensions

import android.content.Context
import android.widget.Toast

fun String.makeToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(context, this, duration).show()
}