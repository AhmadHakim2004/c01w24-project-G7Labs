package com.example.androidassist.apps.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

import com.example.androidassist.apps.settings.SettingsService

class SettingsMainFragment : Fragment() {
    private lateinit var settingsButtonHolder: GridLayout
    private lateinit var languageButton: Button
    private lateinit var volumeButton: Button
    private lateinit var brightnessButton: Button
    private lateinit var textSizeButton: Button
    private lateinit var blindnessButton: Button
    private lateinit var textToSpeechButton: Button
    private lateinit var buttons: List<Button>
    private lateinit var service: SettingsService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_main_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsButtonHolder = view.findViewById(R.id.settingsButtonsHolder)
        // Find the button
        languageButton = view.findViewById(R.id.language)
        volumeButton = view.findViewById(R.id.volume)
        brightnessButton = view.findViewById(R.id.brightness)
        textSizeButton = view.findViewById(R.id.text_size)
        blindnessButton = view.findViewById(R.id.blindness)
        textToSpeechButton = view.findViewById(R.id.tts)

        buttons = listOf(
            languageButton, volumeButton, brightnessButton, textSizeButton,
            blindnessButton, textToSpeechButton
        )

        // Set OnClickListener to the button
        languageButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsLanguageFragment())
            settingsActivity.setState(SharedConstants.AppEnum.SLANGUAGE)
        }

        // Set OnClickListener to the button
        textSizeButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsTextSizeFragment())
            settingsActivity.setState(SharedConstants.AppEnum.STEXT)
        }

        service = SettingsService(requireActivity().window);

        setupStyles()
    }

    private fun setupStyles() {
        LayoutUtils.setPadding(settingsButtonHolder, 0.02f)
        for (button in buttons) {
            LayoutUtils.setPadding(button, 0f, 0.05f, 0f, 0.03f)
            LayoutUtils.setMargins(button, 0.005f)
            LayoutUtils.setTextSize(button, 0.008f)
        }
    }

}