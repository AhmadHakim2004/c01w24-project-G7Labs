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

        fun addStringSetElementToDefaultSharedPrefFile(context: Context, key : String, value : String)
        {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)

            val set = getStringSetFromDefaultSharedPrefFile(context, key, setOf())
            val hashSet = if (set == null) hashSetOf<String>() else HashSet(set)

            hashSet.add(value)

            with (sharedPrefSettings.edit()) {
                putStringSet(key, hashSet)
                apply()
            }
        }

        fun removeStringSetElementFromDefaultSharedPrefFile(context: Context, key : String, value : String)
        {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)

            val set = getStringSetFromDefaultSharedPrefFile(context, key, setOf()) ?: return
            val hashSet = HashSet(set)

            hashSet.remove(value)

            with (sharedPrefSettings.edit()) {
                putStringSet(key, hashSet)
                apply()
            }
        }

        fun getStringSetFromDefaultSharedPrefFile(context: Context, key: String, default : Set<String>) : Set<String>? {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)

            val set = sharedPrefSettings.getStringSet(key, default)
            return set?.let { HashSet(it) }
        }

        fun addIntToDefaultSharedPrefFile(context: Context, key : String, value : Int)
        {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            with (sharedPrefSettings.edit()) {
                putInt(key, value)
                apply()
            }
        }

        fun getIntFromDefaultSharedPrefFile(context: Context, key: String, default : Int) : Int? {
            val sharedPrefSettings = getDefaultSharedPrefFile(context)
            return sharedPrefSettings.getInt(key, default)

        }
    }
}
