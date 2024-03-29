package com.example.androidassist.sharedComponents.dataClasses

import android.content.Intent
import android.graphics.drawable.Drawable

abstract class AdapterItem(
    open val id: String,
)

class CustomApp(
    id: String,
    val nameResource: Int,
    val imageResource: Int? = null,
    val pageState: SharedConstants.PageState
) : AdapterItem(id)

class ActionItem(
    id: String,
    val nameResource: Int,
    val imageResource: Int? = null,
    val pageState: SharedConstants.PageState
) : AdapterItem(id)

class InstalledApp(
    id: String,
    val appName: String,
    val drawable: Drawable? = null,
    val intent: Intent? = null,
    var selected: Boolean = false
) : AdapterItem(id)
