package com.example.androidassist.apps.contacts

import android.graphics.Bitmap

data class ContactInfo(
    var name: String,
    var number: String,
    var image: Bitmap? = null
)