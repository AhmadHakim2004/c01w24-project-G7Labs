package com.example.androidassist.apps.settings

import android.media.AudioManager
import android.widget.Button
import android.content.Context
import android.view.Window
import com.example.androidassist.sharedComponents.AndroidAssistApplication

class SettingsService (private val window: Window) {
    private val context: Context = AndroidAssistApplication.getAppContext()

    fun increaseVolume(button1: Button) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        button1.setOnClickListener {
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            if (currentVolume < maxVolume) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            }
        }
    }

    fun decreaseVolume(button1: Button) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        button1.setOnClickListener {
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            if (currentVolume > 0) {
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            }
        }

    }

    fun increaseBrightness(button: Button) {
        val layoutParams = window.attributes
        val currentBrightness = layoutParams.screenBrightness

        button.setOnClickListener {
            val newBrightness = currentBrightness + 0.1f
            if (newBrightness <= 1.0f) {
                layoutParams.screenBrightness = newBrightness
                window.attributes = layoutParams
            }
        }
    }

    fun decreaseBrightness(button: Button) {
        val layoutParams = window.attributes
        val currentBrightness = layoutParams.screenBrightness

        button.setOnClickListener {
            val newBrightness = currentBrightness - 0.1f
            if (newBrightness >= 0.0f) {
                layoutParams.screenBrightness = newBrightness
                window.attributes = layoutParams
            }
        }
    }
}