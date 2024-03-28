package com.example.androidassist.apps.settings

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import kotlin.math.ln
import kotlin.math.roundToInt


class SettingsService {
    private val context: Context = AndroidAssistApplication.getAppContext()
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun getCurrentVolumePercentage(): Int {
        return (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / maxVolume)
    }

    fun getCurrentBrightnessPercentage(): Int {
        val currentBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        val percentageFloat = ln(currentBrightness.toFloat()) / ln(255f)
        return (percentageFloat * 100).toInt()
    }

    fun increaseVolume() {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (currentVolume < maxVolume) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        }
    }

    fun decreaseVolume() {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (currentVolume > 0) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        }
    }

    fun increaseBrightness() {
        val currentBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        val newBrightness = ((currentBrightness * Math.E).roundToInt()).coerceAtMost(255)
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness)

    }

    fun decreaseBrightness() {
        val currentBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        val newBrightness = ((currentBrightness / Math.E).roundToInt()).coerceAtLeast(1)
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness)
    }
}