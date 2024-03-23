package com.example.androidassist.apps.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.OnRefresh
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.LocaleUtil
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils


class SettingsLanguageFragment : Fragment() {

    private lateinit var settingsButtonHolder: GridLayout
    private lateinit var englishButton: Button
    private lateinit var spanishButton: Button
    private lateinit var frenchButton: Button
    private lateinit var arabicButton: Button
    private lateinit var hindiButton: Button
    private lateinit var mandarinButton: Button
    private lateinit var buttons: List<Button>
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

        buttons = listOf(englishButton, spanishButton, frenchButton, arabicButton,
            hindiButton, mandarinButton)

        setBtnListeners()

        setupStyles()
    }

    private fun setBtnListeners(){
        hindiButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "ur")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "ur")
        }

        englishButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "en")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "en")
        }

        spanishButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "es")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "es")
        }

        frenchButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "fr")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "fr")
        }

        arabicButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "ar")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "ar")
        }

        mandarinButton.setOnClickListener {
            LocaleUtil.setAppLocale(requireContext(), "zh")
            onRefresh?.refreshScreen(SettingsLanguageFragment(), SharedConstants.AppEnum.SLANGUAGE)
            SharedPreferenceUtils.addStringToDefaultSharedPrefFile(requireContext(), "language", "zh")
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
        for (button in buttons) {
            LayoutUtils.setPadding(button, 0f, 0.06f, 0f, 0.03f)
            LayoutUtils.setMargins(button, 0.005f)
            LayoutUtils.setTextSize(button, 0.008f)
        }
    }
}