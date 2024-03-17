package com.example.androidassist.apps.contacts


import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class ContactsMainActivity : BaseApps() {
    override val appInfo: AppsInfo
        get() = SharedConstants.DefaultAppsInfo.ContactsAppInfo

    override fun setupFragment() {
        replaceFragment(ContactMainFragment())
    }

    override fun setupBackButton() {
        //TODO("Not yet implemented")
    }

}