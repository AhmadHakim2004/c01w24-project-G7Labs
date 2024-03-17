package com.example.androidassist.apps.camera

import android.content.Intent
import android.os.Bundle
import com.example.androidassist.MainActivity
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
        setState(SharedConstants.AppEnum.CAMERA)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener{
            when(getState()) {
                SharedConstants.AppEnum.CAMERA -> startActivity(Intent(this, MainActivity::class.java))
                else -> {
                    setState(SharedConstants.AppEnum.CAMERA)
                    replaceFragment(CameraMainFragment())}
            }
        }
    }
}