package com.example.androidassist.apps.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

class SettingsTextSizeFragment : Fragment() {
    private lateinit var textSizeButtonHolder: GridLayout
    private lateinit var size1Button: Button
    private lateinit var size2Button: Button
    private lateinit var size3Button: Button
    private lateinit var size4Button: Button
    private lateinit var size5Button: Button
    private lateinit var size6Button: Button
    private lateinit var buttons: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_text_size_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textSizeButtonHolder = view.findViewById(R.id.textSizeButtonsHolder)
        // Find the button
        size1Button = view.findViewById(R.id.size_1)
        size2Button = view.findViewById(R.id.size_2)
        size3Button = view.findViewById(R.id.size_3)
        size4Button = view.findViewById(R.id.size_4)
        size5Button = view.findViewById(R.id.size_5)
        size6Button = view.findViewById(R.id.size_6)

        buttons = listOf(
            size1Button, size2Button, size3Button, size4Button,
            size5Button, size6Button
        )

        setupStyles()
    }

    private fun setupStyles() {
        LayoutUtils.setPadding(textSizeButtonHolder, 0.02f)
        for (button in buttons) {
            LayoutUtils.setPadding(button, 0f, 0.07f, 0f, 0f)
            LayoutUtils.setMargins(button, 0.005f)
        }
    }
}