package com.example.androidassist.sharedComponents.utilities

import android.content.res.Configuration
import java.util.Locale
import android.content.Context

class LocaleUtils {

    companion object{
        @Suppress("DEPRECATION")
        fun setAppLocale(context: Context, localeCode: String) {
            val resources = context.resources
            val dm = resources.displayMetrics
            val config: Configuration = resources.configuration
            config.setLocale(Locale(localeCode.toLowerCase(Locale.getDefault())))
            resources.updateConfiguration(config, dm)
        }
    }
}