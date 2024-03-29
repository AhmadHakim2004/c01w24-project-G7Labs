package com.example.androidassist.apps.contacts


import android.content.Intent
import com.example.androidassist.MainActivity
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants
import com.example.androidassist.sharedComponents.views.BaseApps

class ContactsMainActivity : BaseApps() {
    var selectedContact: ContactInfo? = null

    override val appInfo: CustomApp
        get() = SharedConstants.DefaultApps.ContactsApp

    override fun setupFragment() {
        replaceFragment(ContactMainFragment())
        setState(SharedConstants.PageState.CONTACTS)
    }

    override fun setupBackButton() {
        backButton.setOnClickListener{
            when(getState()) {
                SharedConstants.PageState.CONTACTS -> startActivity(Intent(this, MainActivity::class.java))
                else -> {
                    selectedContact = null
                    replaceFragment(ContactMainFragment())
                    setState(SharedConstants.PageState.CONTACTS)
                }
            }
        }
    }
}