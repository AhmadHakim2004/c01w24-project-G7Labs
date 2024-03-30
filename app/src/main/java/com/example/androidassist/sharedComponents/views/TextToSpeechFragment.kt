package com.example.androidassist.sharedComponents.views

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.fragment.app.Fragment
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import com.example.androidassist.sharedComponents.utilities.TextToSpeechUtils
import java.util.Locale

abstract class TextToSpeechFragment : Fragment(), TextToSpeech.OnInitListener {
    protected lateinit var textToSpeech: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textToSpeech = TextToSpeech(context, this)
    }

    fun setupTTS(view: View, text: CharSequence) {
        TextToSpeechUtils.setupTTS(view, textToSpeech, text)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val lang = resources.configuration.locales.get(0)
            val result = textToSpeech.setLanguage(lang)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.CANADA)
            }
        }
    }

    override fun onDestroyView() {
        // Shutdown Text-to-Speech engine when fragment view is destroyed to release resources
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroyView()
    }
}