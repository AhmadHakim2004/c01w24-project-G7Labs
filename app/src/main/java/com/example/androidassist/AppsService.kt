package com.example.androidassist

import android.content.ComponentName
import android.content.Intent
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class AppsService {
    companion object {
        fun getAllApps(): MutableList<InstalledApp> {
            val packageManager = AndroidAssistApplication.getAppContext().packageManager
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            val apps = ArrayList<InstalledApp>()
            val savedSelectedApps = SharedPreferenceUtils.getStringSetFromDefaultSharedPrefFile(
                AndroidAssistApplication.getAppContext(), "SelectedApps", setOf()
            )
            val selectedApps = savedSelectedApps?.toMutableList() ?: mutableListOf()

            // get list of all the apps installed
            val ril = packageManager.queryIntentActivities(mainIntent, 0)

            for (ri in ril) {
                if (ri.activityInfo != null && ri.activityInfo.packageName != "com.example.androidassist") {
                    // get package
                    val res = packageManager.getResourcesForApplication(ri.activityInfo.applicationInfo)
                    // if activity label res is found
                    val id = ri.activityInfo.applicationInfo.uid.toString()

                    val name = if (ri.activityInfo.labelRes != 0) {
                        res.getString(ri.activityInfo.labelRes)
                    } else {
                        ri.activityInfo.applicationInfo.loadLabel(packageManager).toString()
                    }

                    val icon = if (ri.activityInfo.icon != 0) {
                        ri.activityInfo.loadIcon(packageManager)
                    } else {
                        ri.activityInfo.applicationInfo.loadIcon(packageManager)
                    }

                    val intent = Intent().apply {
                        component = ComponentName(ri.activityInfo.packageName, ri.activityInfo.name)
                    }

                    val selected = selectedApps.contains(id)

                    apps.add(InstalledApp(id, name, icon, intent, selected))
                }
            }

            return apps
        }
    }
}