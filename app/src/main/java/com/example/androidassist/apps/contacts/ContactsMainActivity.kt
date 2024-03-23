package com.example.androidassist.apps.contacts


import android.content.Intent
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class ContactsMainActivity : BaseApps() {
    override val appInfo: AppsInfo
        get() = SharedConstants.DefaultAppsInfo.ContactsAppInfo

    override fun setupFragment() {
        replaceFragment(ContactMainFragment())
        setState(SharedConstants.AppEnum.CONTACTS)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener{
            when(getState()) {
                SharedConstants.AppEnum.CONTACTS -> startActivity(Intent(this, MainActivity::class.java))
                else -> {
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.AppEnum.CONTACTS)
                }
            }
        }
    }







}