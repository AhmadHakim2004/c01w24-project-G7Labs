package com.example.androidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import com.example.androidassist.sharedComponents.utilities.TextToSpeechUtils
import java.util.Locale

class MainSelectAppsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var applyChangesButton: Button
    private lateinit var discardChangesButton: Button

    private lateinit var apps: MutableList<InstalledApp>
    private lateinit var appsRecyclerViewAdapter: AppsRecyclerViewAdapter

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_select_apps_activity)

        appsRecyclerView = findViewById(R.id.appsRecyclerContainer)
        applyChangesButton = findViewById(R.id.apply_apps_changes)
        discardChangesButton = findViewById(R.id.discard_apps_changes)

        textToSpeech = TextToSpeech(this, this)

        initAppGrid()
        setupOnClickListeners()
    }

    private fun initAppGrid() {
        apps = AppsService.getAllApps()

        appsRecyclerViewAdapter = AppsRecyclerViewAdapter(apps)
        appsRecyclerView.adapter = appsRecyclerViewAdapter

        val layoutManager = GridLayoutManager(this, 2)
        appsRecyclerView.layoutManager = layoutManager
    }
    private fun setupOnClickListeners() {
        applyChangesButton.setOnClickListener {
            SharedPreferenceUtils.addStringSetToDefaultSharedPrefFile(
                AndroidAssistApplication.getAppContext(), "SelectedApps", appsRecyclerViewAdapter.selectedApps.toHashSet()
            )
            startActivity(Intent(this, MainActivity::class.java))
        }

        discardChangesButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        TextToSpeechUtils.setupTTS(applyChangesButton, textToSpeech, applyChangesButton.text)
        TextToSpeechUtils.setupTTS(discardChangesButton, textToSpeech, discardChangesButton.text)
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
}