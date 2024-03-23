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
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class SettingsTextSizeFragment : Fragment() {
    private lateinit var textSizeButtonHolder: GridLayout
    private lateinit var size1Button: Button
    private lateinit var size2Button: Button
    private lateinit var size3Button: Button
    private lateinit var size4Button: Button
    private lateinit var size5Button: Button
    private lateinit var size6Button: Button
    private lateinit var buttons: List<Pair<Button, Float>>
    private var onRefresh: OnRefresh? = null


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
            Pair(size1Button, 0.5f),
            Pair(size2Button, 0.75f),
            Pair(size3Button, 1f),
            Pair(size4Button, 1.2f),
            Pair(size5Button, 1.4f),
            Pair(size6Button, 1.5f)
        )
        setBtnListeners()

        setupStyles()

    }

    private fun setBtnListeners(){
        buttons.forEachIndexed { _, pair ->
            pair.first.setOnClickListener {
                val size = pair.second
                LayoutUtils.setAppTextSize(requireContext(), size)
                onRefresh?.refreshScreen(SettingsTextSizeFragment(), SharedConstants.AppEnum.STEXT)
                SharedPreferenceUtils.addFloatToDefaultSharedPrefFile(
                    requireContext(),
                    "textSize",
                    size
                )
            }
        }
    }
    private fun setupStyles() {
        LayoutUtils.setPadding(textSizeButtonHolder, 0.02f)
        for (pair in buttons) {
            LayoutUtils.setPadding(pair.first, 0f, 0.05f, 0f, 0.03f)
            LayoutUtils.setMargins(pair.first, 0.005f)
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




}