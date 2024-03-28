package com.example.androidassist.apps.settings

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils


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

        // Set OnClickListener to the language button
        languageButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsLanguageFragment())
            settingsActivity.setState(SharedConstants.AppEnum.SLANGUAGE)
        }

        // Set OnClickListener to the text_size button
        textSizeButton.setOnClickListener {
            val settingsActivity = activity as SettingsMainActivity
            settingsActivity.replaceFragment(SettingsTextSizeFragment())
            settingsActivity.setState(SharedConstants.AppEnum.STEXT)
        }

        // Set OnClickListener to the volume button
        volumeButton.setOnClickListener {
            showVolumeDialog()
        }

        // Set OnClickListener to the brightness button
        brightnessButton.setOnClickListener {
            showBrightnessDialog()
        }

        setupStyles()
    }

    private fun showVolumeDialog() {
        service = SettingsService()
        val volumeDialog = Dialog(requireActivity())
        volumeDialog.setContentView(R.layout.custom_dialog)

        val dialogTitle = volumeDialog.findViewById<TextView>(R.id.dialog_title)
        val dialogInfo = volumeDialog.findViewById<TextView>(R.id.dialog_info)
        val btnDecrease = volumeDialog.findViewById<Button>(R.id.btn_decrease)
        val btnIncrease = volumeDialog.findViewById<Button>(R.id.btn_increase)

        dialogTitle.text = getString(R.string.volume)
        dialogInfo.text = "${service.getCurrentVolumePercentage()} %"

        volumeDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        btnIncrease.setOnClickListener {
            service.increaseVolume()
            Handler(Looper.getMainLooper()).postDelayed({
                dialogInfo.text = "${service.getCurrentVolumePercentage()} %"
            }, 50)
        }

        btnDecrease.setOnClickListener {
            service.decreaseVolume()
            Handler(Looper.getMainLooper()).postDelayed({
                dialogInfo.text = "${service.getCurrentVolumePercentage()} %"
            }, 50)
        }

        volumeDialog.setCancelable(true)
        volumeDialog.show()
    }

    private fun showBrightnessDialog() {
        service = SettingsService()
        val brightnessDialog = Dialog(requireActivity())
        brightnessDialog.setContentView(R.layout.custom_dialog)

        val dialogTitle = brightnessDialog.findViewById<TextView>(R.id.dialog_title)
        val dialogInfo = brightnessDialog.findViewById<TextView>(R.id.dialog_info)
        val btnDecrease = brightnessDialog.findViewById<Button>(R.id.btn_decrease)
        val btnIncrease = brightnessDialog.findViewById<Button>(R.id.btn_increase)

        dialogTitle.text = getString(R.string.brightness)
        dialogInfo.text = "${service.getCurrentBrightnessPercentage()} %"

        brightnessDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        btnIncrease.setOnClickListener {
            service.increaseBrightness()
            dialogInfo.text = "${service.getCurrentBrightnessPercentage()} %"
        }

        btnDecrease.setOnClickListener {
            service.decreaseBrightness()
            dialogInfo.text = "${service.getCurrentBrightnessPercentage()} %"
        }

        brightnessDialog.setCancelable(true)

        if (Settings.System.canWrite(context)) {
            brightnessDialog.show()
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + requireContext().packageName))
            startActivity(intent)
        }
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