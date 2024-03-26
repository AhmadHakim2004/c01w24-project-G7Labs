package com.example.androidassist.apps.settings

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import com.example.androidassist.sharedComponents.AndroidAssistApplication


class SettingsService {
    private val context: Context = AndroidAssistApplication.getAppContext()
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun getCurrentVolumePercentage(): Int {
        return (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / maxVolume)
    }

    fun getCurrentBrightnessPercentage(): Int {
        return (Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)  * 100 / 255)
    }

    fun changeVolume(progress: Int) {
        val volumeProgress = progress * maxVolume / 100
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeProgress, 0)
    } // seek

    fun changeBrightness(progress: Int) {
        val brightnessProgress = progress * 255/100
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightnessProgress)
    } // seek

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
        val newBrightness = (currentBrightness + 10).coerceAtMost(255)
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness)

    }

    fun decreaseBrightness() {
        val currentBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        val newBrightness = (currentBrightness - 10).coerceAtLeast(0)
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness)
    }
}