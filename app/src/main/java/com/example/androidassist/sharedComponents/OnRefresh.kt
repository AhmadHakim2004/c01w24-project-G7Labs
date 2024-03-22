package com.example.androidassist.sharedComponents

import androidx.fragment.app.Fragment
import com.example.androidassist.sharedComponents.dataClasses.SharedConstants


/**
 * An interface that allows you to communicate with the parent activity through its fragment
 * @see SettingsMainAcitivty with SettingsLanguageFragment
 */
interface OnRefresh {
    fun refreshScreen(fragment : Fragment, state : SharedConstants.AppEnum)
}
