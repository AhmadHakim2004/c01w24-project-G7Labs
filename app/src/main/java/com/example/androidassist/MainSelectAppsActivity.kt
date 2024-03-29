package com.example.androidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class MainSelectAppsActivity : AppCompatActivity() {
    private lateinit var appsRecyclerView: RecyclerView
    private lateinit var applyChangesButton: Button
    private lateinit var discardChangesButton: Button

    private lateinit var apps: MutableList<InstalledApp>
    private lateinit var appsRecyclerViewAdapter: AppsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_select_apps_activity)

        appsRecyclerView = findViewById(R.id.appsRecyclerContainer)
        applyChangesButton = findViewById(R.id.apply_apps_changes)
        discardChangesButton = findViewById(R.id.discard_apps_changes)

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
    }
}