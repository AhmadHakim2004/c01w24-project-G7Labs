package com.example.androidassist.sharedComponents.dataClasses

import com.example.androidassist.R

class SharedConstants {
    enum class AppEnum {
        CAMERA,
        PHOTOS,
        CONTACTS,
        SETTINGS,
        OTHER
    }

    class DefaultAppsInfo {
        companion object {
            val CameraAppInfo = AppsInfo(1, R.mipmap.ic_launcher, "Camera", AppEnum.CAMERA)
            val PhotosAppInfo = AppsInfo(2, R.mipmap.ic_launcher, "Photos", AppEnum.PHOTOS)
            val ContactsAppInfo = AppsInfo(3, R.mipmap.ic_launcher, "Contacts", AppEnum.CONTACTS)
            val SettingsAppInfo = AppsInfo(4, R.mipmap.ic_launcher, "Settings", AppEnum.SETTINGS)
        }
    }
}