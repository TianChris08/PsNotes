package com.example.psnotes.ui.components

import android.content.Context
import android.widget.Toast

var lastToast: Toast? = null

fun showToast(context: Context, message: String) {
    lastToast?.cancel()
    lastToast = Toast.makeText(context, message, Toast.LENGTH_SHORT).also { it.show() }
}