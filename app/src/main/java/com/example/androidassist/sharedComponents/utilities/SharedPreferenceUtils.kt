package com.example.androidassist.sharedComponents.utilities

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences

class SharedPreferenceUtils {

    companion object{

        fun getDefaultSharedPrefFile(context : Context) : SharedPreferences {
            return getDefaultSharedPreferences(context)
        }

        fun addStringToDefaultSharedPrefFile(context: Context, key : String, value : String)
        {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            with (sharedPrefSettings.edit()) {
                putString(key, value)
                apply()
            }
        }
    }
}
