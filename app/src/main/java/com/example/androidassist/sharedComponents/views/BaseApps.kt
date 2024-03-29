package com.example.androidassist.sharedComponents.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

abstract class BaseApps : AppCompatActivity() {
    protected lateinit var appHeader: LinearLayout
    protected lateinit var appHeaderIcon: ImageView
    protected lateinit var appHeaderTitle: TextView
    protected lateinit var backButton: Button
    private lateinit var state : SharedConstants.PageState

    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = SharedPreferenceUtils.getIntFromDefaultSharedPrefFile(applicationContext, "theme", R.style.Theme_AndroidAssist)
        if (theme != null) {
            setTheme(theme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_apps)

        appHeader = findViewById(R.id.appHeader)
        appHeaderIcon = findViewById(R.id.appHeaderIcon)
        appHeaderTitle = findViewById(R.id.appHeaderTitle)
        backButton = findViewById(R.id.backButton)

        hideStatusBar()
        setupHeader()
        setupFragment()
        setupBackButton()
        setupStyles()
    }

    abstract val appInfo: CustomApp

    abstract fun setupFragment()

    abstract fun setupBackButton()

    fun replaceFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, newFragment).commit()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupHeader() {
        this.appHeaderIcon.setImageResource(appInfo.imageResource!!)
        this.appHeaderTitle.text = resources.getString(appInfo.nameResource)
    }

    private fun setupStyles() {
        LayoutUtils.setMargins(appHeader, 0f, 0.005f, 0f, 0.005f)
        LayoutUtils.setPadding(appHeaderIcon, 0.012f)
        LayoutUtils.setTextSize(appHeaderTitle, 0.009f)
        LayoutUtils.setMargins(backButton, 0.025F, 0.01F, 0.025F, 0.01F)
        LayoutUtils.setTextSize(backButton, 0.007f)
    }

    fun setState(state : SharedConstants.PageState){
        this.state = state
    }

    fun getState(): SharedConstants.PageState {
        return state
    }
}
