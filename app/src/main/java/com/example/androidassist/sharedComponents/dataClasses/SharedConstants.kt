package com.example.androidassist.sharedComponents.dataClasses

import com.example.androidassist.R

class SharedConstants {
    enum class PageState {
        CAMERA,
        PHOTOS,
        CONTACTS,
        SETTINGS,
        CPHOTO,
        CPHOTOSELFIE,
        CVIDEO,
        CVIDEOSELFIE,
        PSINGLEPHOTO,
        STEXT,
        SLANGUAGE,
        CADDCONTACTS,
        CEMERGENCYCONFIRM,
        CSINGLECONTACT,
        CEDITCONTACTS,
        CEMERGENCY,
        CCALLSCREEN
    }

    class DefaultApps {
        companion object {
            val CameraApp = CustomApp("1", R.string.camera, R.drawable.camera_icon, PageState.CAMERA)
            val PhotosApp = CustomApp("2", R.string.photo, R.drawable.photos_icon, PageState.PHOTOS)
            val ContactsApp = CustomApp("3", R.string.contacts, R.drawable.contacts_icon, PageState.CONTACTS)
            val SettingsApp = CustomApp("4", R.string.settings, R.drawable.settings_icon, PageState.SETTINGS)
        }
    }
}