package com.example.androidassist.sharedComponents

import android.app.Application
import android.content.Context

class AndroidAssistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        androidAssistAppInstance = this
    }

    companion object {
        private lateinit var androidAssistAppInstance: AndroidAssistApplication

        fun getAppContext(): Context {
            return androidAssistAppInstance
        }
    }
}