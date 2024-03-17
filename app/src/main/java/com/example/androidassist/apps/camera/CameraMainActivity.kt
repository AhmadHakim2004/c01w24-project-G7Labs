package com.example.androidassist.apps.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.androidassist.MainActivity
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
        setState(SharedConstants.AppEnum.CAMERA)
        replaceFragment(CameraMainFragment())
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