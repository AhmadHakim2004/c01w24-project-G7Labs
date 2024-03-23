package com.example.androidassist.apps.settings

import android.content.Intent
import android.os.Bundle
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class SettingsMainActivity : BaseApps() {
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
}