package com.example.androidassist.sharedComponents.dataClasses

import com.example.androidassist.R

class SharedConstants {
    enum class AppEnum {
        CAMERA,
        PHOTOS,
        CONTACTS,
        SETTINGS,
        OTHER,
        //TODO: //need to find a better fix for this...generic adapter maybe
        CPHOTO,
        CPHOTOSELFIE,
        CVIDEO,
        CVIDEOSELFIE,
        STEXT,
        SLANGUAGE,
        CADDCONTACTS,
        CEMERGENCYCONFIRM,
        CSINGLECONTACT,
        CEDITCONTACTS
    }

    class DefaultAppsInfo {
        companion object {
            val CameraAppInfo = AppsInfo(1, R.drawable.camera_icon, R.string.camera, AppEnum.CAMERA)
            val PhotosAppInfo = AppsInfo(2, R.drawable.photos_icon, R.string.photo, AppEnum.PHOTOS)
            val ContactsAppInfo = AppsInfo(3, R.drawable.contacts_icon, R.string.contacts, AppEnum.CONTACTS)
            val SettingsAppInfo = AppsInfo(4, R.drawable.settings_icon, R.string.settings, AppEnum.SETTINGS)
        }
    }
}