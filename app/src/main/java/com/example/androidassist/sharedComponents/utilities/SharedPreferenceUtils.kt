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

        fun getStringFromDefaultSharedPrefFile(context: Context, key: String, default : String) : String? {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            return sharedPrefSettings.getString(key, default)

        }

        fun addFloatToDefaultSharedPrefFile(context: Context, key : String, value : Float)
        {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            with (sharedPrefSettings.edit()) {
                putFloat(key, value)
                apply()
            }
        }

        fun getFloatFromDefaultSharedPrefFile(context: Context, key: String, default : Float) : Float? {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            return sharedPrefSettings.getFloat(key, default)

        }
    }
}
