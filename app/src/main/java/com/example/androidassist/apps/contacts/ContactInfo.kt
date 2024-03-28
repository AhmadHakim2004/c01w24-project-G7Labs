package com.example.androidassist.apps.contacts

import android.graphics.Bitmap

data class ContactInfo(
    var firstName: String?,
    var lastName: String?,
    var number: String,
    var image: Bitmap? = null
)