package com.example.androidassist.apps.settings

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.OnRefresh
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class SettingsMainActivity : BaseApps(), OnRefresh {
    override val appInfo: AppsInfo
        get() = SharedConstants.DefaultAppsInfo.SettingsAppInfo

    override fun setupFragment() {
        replaceFragment(SettingsMainFragment())
        setState(SharedConstants.AppEnum.SETTINGS)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener{
            when(getState()) {
                SharedConstants.AppEnum.SETTINGS -> startActivity(Intent(this, MainActivity::class.java))
                else -> {
                    setState(SharedConstants.AppEnum.SETTINGS)
                    replaceFragment(SettingsMainFragment())
                }
            }
        }
    }

    override fun refreshScreen(fragment: Fragment, state: SharedConstants.AppEnum) {
        finish()
        overridePendingTransition(0, 0) // Optional: remove transition animation
        startActivity(intent)
    }
}