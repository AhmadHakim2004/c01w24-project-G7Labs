package com.example.androidassist.sharedComponents.utilities

import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.ActionItem
import com.example.androidassist.sharedComponents.dataClasses.AdapterItem
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp

class TextToSpeechUtils {
    companion object {
        fun setupTTS(view: View, textToSpeech: TextToSpeech, text: CharSequence) {
            view.setOnLongClickListener {
                if(isTTSEnabled()){
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
                }
                return@setOnLongClickListener true
            }
        }

        fun setupTTS(gridView: GridView, items: List<AdapterItem>, textToSpeech: TextToSpeech) {
            gridView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
                if(isTTSEnabled()) {
                    when (val app = items[position]) {
                        is ActionItem -> {
                            textToSpeech.speak(AndroidAssistApplication.getAppContext().getText(app.nameResource),
                                TextToSpeech.QUEUE_FLUSH, null, "")
                        }
                        is CustomApp -> {
                            textToSpeech.speak(AndroidAssistApplication.getAppContext().getText(app.nameResource),
                                TextToSpeech.QUEUE_FLUSH, null, "")
                        }
                        is InstalledApp -> {
                            textToSpeech.speak(app.appName, TextToSpeech.QUEUE_FLUSH, null, "")
                        }
                    }
                }
                return@OnItemLongClickListener true
            }
        }

        private fun isTTSEnabled(): Boolean {
            return SharedPreferenceUtils.getIntFromDefaultSharedPrefFile(
                AndroidAssistApplication.getAppContext(), "TTS", 0) == 1
        }
    }
}