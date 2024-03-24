package com.example.androidassist.apps.photos

import android.content.Intent
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class PhotosMainActivity : BaseApps() {
    override val appInfo: AppsInfo
        get() = SharedConstants.DefaultAppsInfo.PhotosAppInfo

    override fun setupFragment() {
        replaceFragment(PhotosMainFragment())
        setState(SharedConstants.AppEnum.PHOTOS)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener {
            when (getState()) {
                SharedConstants.AppEnum.PHOTOS -> startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                else -> {
                    setState(SharedConstants.AppEnum.PHOTOS)
                    replaceFragment(PhotosMainFragment())
                }
            }
        }
    }
}