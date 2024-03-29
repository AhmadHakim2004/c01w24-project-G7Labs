package com.example.androidassist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.androidassist.apps.settings.SettingsMainActivity
import com.example.androidassist.apps.camera.CameraMainActivity
import com.example.androidassist.apps.contacts.ContactsMainActivity
import com.example.androidassist.apps.photos.PhotosMainActivity
import com.example.androidassist.sharedComponents.dataClasses.AdapterItem
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var appsGridContainer: GridView
    private lateinit var appsGridAdapter: GridAdapter
    private lateinit var apps: MutableList<AdapterItem>
    private var dateDisplay: TextView? = null
    private lateinit var batteryDisplay: ProgressBar
    private var batteryProgressStatus = 0
    private lateinit var addAppsBtn: Button
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = SharedPreferenceUtils.getIntFromDefaultSharedPrefFile(applicationContext, "theme", R.style.Theme_AndroidAssist)
        if (theme != null) {
            setTheme(theme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())

        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        initAppGrid()
        initTimeAndDate()
        initBattery()

        addAppsBtn = findViewById(R.id.add_apps)
        addAppsBtn.setOnClickListener {
            startActivity(Intent(this, MainSelectAppsActivity::class.java))
        }
        setStyles()
    }

    private fun addPhoneApps(apps: MutableList<AdapterItem>) {
        var phoneApps = AppsService.getAllApps()
        val selectedApps = SharedPreferenceUtils.getStringSetFromDefaultSharedPrefFile(
            applicationContext, "SelectedApps", setOf())

        if (selectedApps.isNullOrEmpty()) {
            return
        }

        phoneApps = phoneApps.filter { app -> selectedApps.contains(app.id) }.toMutableList()

        for (app in phoneApps) {
            apps.add(app)
        }
    }

    private fun initAppGrid() {
        appsGridContainer = findViewById(R.id.appsGridContainer)
        apps = getInitialApps()
        addPhoneApps(apps)

        appsGridAdapter = GridAdapter(this, apps)
        appsGridContainer.adapter = appsGridAdapter

        appsGridContainer.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = apps[position]
            if(item is CustomApp) {
                when(item.pageState) {
                    SharedConstants.PageState.CAMERA -> startActivity(Intent(this, CameraMainActivity::class.java))
                    SharedConstants.PageState.PHOTOS -> startActivity(Intent(this, PhotosMainActivity::class.java))
                    SharedConstants.PageState.CONTACTS -> startActivity(Intent(this, ContactsMainActivity::class.java))
                    SharedConstants.PageState.SETTINGS -> startActivity(Intent(this, SettingsMainActivity::class.java))
                    else -> {}
                }
            }
            else if (item is InstalledApp) {
                startActivity(item.intent)
            }
        }
    }

    fun initTimeAndDate() {
        //time and date
        dateDisplay = findViewById(R.id.tv_date)
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        dateDisplay?.let {
            it.text = currentDate
        }
    }

    private fun initBattery() {
        //battery
        mContext = applicationContext
        val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        mContext.registerReceiver(batteryBroadcastReceiver, iFilter)
        batteryDisplay = findViewById(R.id.pb_battery)
    }

    private val batteryBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val percentage = level.toFloat() / scale
            batteryProgressStatus = (percentage * 100).toInt()
            if (batteryProgressStatus <= 25){
                batteryDisplay.progressTintList = android.content.res.ColorStateList.valueOf(Color.RED)
            } else {
                batteryDisplay.progressTintList = android.content.res.ColorStateList.valueOf(Color.GREEN)
            }
            batteryDisplay.progress = batteryProgressStatus
        }
    }

    private fun getInitialApps(): MutableList<AdapterItem> {
        return mutableListOf (
            SharedConstants.DefaultApps.CameraApp,
            SharedConstants.DefaultApps.PhotosApp,
            SharedConstants.DefaultApps.ContactsApp,
            SharedConstants.DefaultApps.SettingsApp
        )
    }

    private fun setStyles() {
        LayoutUtils.setMargins(appsGridContainer, 0.025f, 0.015f, 0.025f, 0f)

        LayoutUtils.setMargins(addAppsBtn, 0.01f, 0.01f, 0.01f, 0.01f)
        LayoutUtils.setPadding(addAppsBtn, 0f, 0.03f, 0f, 0.03f)
        LayoutUtils.setTextSize(addAppsBtn, 0.01f)
    }
}