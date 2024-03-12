package com.example.androidassist.apps.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.androidassist.R
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class CameraMainActivity : BaseApps() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val appInfo: AppsInfo
        get() = SharedConstants.DefaultAppsInfo.CameraAppInfo

    override fun setupFragment() {
        replaceFragment(CameraMainFragment())
    }

    override fun setupBackButton() {
    }
}