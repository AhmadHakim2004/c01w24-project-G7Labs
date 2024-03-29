package com.example.androidassist.apps.camera

import android.content.Intent
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class CameraMainActivity : BaseApps() {
    override val appInfo: CustomApp
        get() = SharedConstants.DefaultApps.CameraApp

    override fun setupFragment() {
        replaceFragment(CameraMainFragment())
        setState(SharedConstants.PageState.CAMERA)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener{
            when(getState()) {
                SharedConstants.PageState.CAMERA -> startActivity(Intent(this, MainActivity::class.java))
                else -> {
                    setState(SharedConstants.PageState.CAMERA)
                    replaceFragment(CameraMainFragment())
                }
            }
        }
    }
}