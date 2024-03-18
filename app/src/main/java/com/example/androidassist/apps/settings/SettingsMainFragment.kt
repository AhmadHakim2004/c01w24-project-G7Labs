package com.example.androidassist.apps.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants

class SettingsMainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the button
        val languageButton = view.findViewById<Button>(R.id.language)

        // Set OnClickListener to the button
        languageButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsLanguage())
            settingsActivity.setState(SharedConstants.AppEnum.SLANGUAGE)
        }

        // Find the button
        val textSizeButton = view.findViewById<Button>(R.id.text_size)

        // Set OnClickListener to the button
        textSizeButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsTextSize())
            settingsActivity.setState(SharedConstants.AppEnum.STEXT)
        }
    }
}