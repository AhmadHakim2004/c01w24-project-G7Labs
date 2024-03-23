package com.example.androidassist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.LocaleUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class SplashScreen : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppLanguage()
        setTextSize()
        setContentView(R.layout.activity_splash_screen)

//      code for api level below 31
//         This is used to hide the status bar and make
//         the splash screen as a full screen activity.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500) // 3000 is the delayed time in milliseconds.
    }

    private fun setAppLanguage(){
//        val sharedPrefSettings = SharedPreferenceUtils.getDefaultSharedPrefFile(applicationContext)
        val lang = SharedPreferenceUtils.getStringFromDefaultSharedPrefFile(applicationContext, "language", "en")
        if (lang != null) {
            LocaleUtils.setAppLocale(baseContext, lang)
        }
    }

    private fun setTextSize(){
        val textSize = SharedPreferenceUtils.getFloatFromDefaultSharedPrefFile(applicationContext, "textSize", 1f)
        if (textSize != null) {
           LayoutUtils.setAppTextSize(baseContext, textSize)
        }
    }

}