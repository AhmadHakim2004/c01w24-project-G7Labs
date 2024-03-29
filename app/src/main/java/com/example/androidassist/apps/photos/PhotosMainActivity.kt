package com.example.androidassist.apps.photos

import android.content.Intent
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class PhotosMainActivity : BaseApps() {
    override val appInfo: CustomApp
        get() = SharedConstants.DefaultApps.PhotosApp

    override fun setupFragment() {
        replaceFragment(PhotosMainFragment())
        setState(SharedConstants.PageState.PHOTOS)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener {
            when (getState()) {
                SharedConstants.PageState.PHOTOS -> startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                else -> {
                    setState(SharedConstants.PageState.PHOTOS)
                    replaceFragment(PhotosMainFragment())
                }
            }
        }
    }
}