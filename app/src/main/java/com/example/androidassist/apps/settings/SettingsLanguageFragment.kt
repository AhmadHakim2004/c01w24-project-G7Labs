package com.example.androidassist.apps.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.OnRefresh
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.LocaleUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import com.example.androidassist.sharedComponents.views.TextToSpeechFragment


class SettingsLanguageFragment : TextToSpeechFragment() {

    private lateinit var settingsButtonHolder: GridLayout
    private lateinit var englishButton: Button
    private lateinit var spanishButton: Button
    private lateinit var frenchButton: Button
    private lateinit var arabicButton: Button
    private lateinit var hindiButton: Button
    private lateinit var mandarinButton: Button
    private lateinit var buttons: List<Pair<Button, String>>
    private var onRefresh: OnRefresh? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_language_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsButtonHolder = view.findViewById(R.id.settingsButtonsHolder)
        // Find the button
        englishButton = view.findViewById(R.id.english)
        spanishButton = view.findViewById(R.id.spanish)
        frenchButton = view.findViewById(R.id.french)
        arabicButton = view.findViewById(R.id.arabic)
        hindiButton = view.findViewById(R.id.hindi)
        mandarinButton = view.findViewById(R.id.mandarin)

        buttons = listOf(
            Pair(englishButton, "en"),
            Pair(spanishButton, "es"),
            Pair(frenchButton, "fr"),
            Pair(arabicButton, "ar"),
            Pair(hindiButton, "ur"),
            Pair(mandarinButton, "zh"))

        setBtnListeners()

        setupStyles()
    }

    private fun setBtnListeners(){

        buttons.forEachIndexed{_, pair ->
            pair.first.setOnClickListener {
                val lang = pair.second
                LocaleUtils.setAppLocale(requireContext(), lang)
                onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.PageState.SLANGUAGE)
                SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", lang)
            }

            setupTTS(pair.first, pair.first.text)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRefresh) {
            onRefresh = context
        } else {
            throw RuntimeException("$context must implement OnDataRefreshListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onRefresh = null
    }

    private fun setupStyles() {
        LayoutUtils.setPadding(settingsButtonHolder, 0.02f)
        for (pair in buttons) {
            LayoutUtils.setPadding(pair.first, 0f, 0.06f, 0f, 0.03f)
            LayoutUtils.setMargins(pair.first, 0.005f)
            LayoutUtils.setTextSize(pair.first, 0.008f)
        }
    }
}