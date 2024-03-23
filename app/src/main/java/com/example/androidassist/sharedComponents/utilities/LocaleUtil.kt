package com.example.androidassist.sharedComponents.utilities

import android.content.res.Configuration
import android.os.Build
import java.util.Locale
import android.content.Context

class LocaleUtil {

    companion object{
        fun setAppLocale(context: Context, localeCode: String) {
            val resources = context.resources
            val dm = resources.displayMetrics
            val config: Configuration = resources.configuration

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(Locale(localeCode.toLowerCase(Locale.getDefault())))
            } else {
                @Suppress("DEPRECATION")
                config.locale = Locale(localeCode.toLowerCase(Locale.getDefault()))
            }

            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, dm)
        }
    }
}