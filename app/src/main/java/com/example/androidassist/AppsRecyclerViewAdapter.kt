package com.example.androidassist

import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils
import com.example.androidassist.sharedComponents.utilities.TextToSpeechUtils
import java.util.Locale

class AppsRecyclerViewAdapter(private val activity: AppCompatActivity, private val items: MutableList<InstalledApp>) :
    RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder>(), TextToSpeech.OnInitListener {
    val context = AndroidAssistApplication.getAppContext()
    var selectedApps: MutableList<String> = SharedPreferenceUtils.getStringSetFromDefaultSharedPrefFile(
        context, "SelectedApps", setOf())?.toMutableList() ?: mutableListOf()

    private val textToSpeech: TextToSpeech = TextToSpeech(context, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_app_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appItem = items[position]

        setupElements(holder, appItem)
        setupSelectingApp(holder, appItem, position)
        TextToSpeechUtils.setupTTS(holder.itemView, textToSpeech, appItem.appName)
        setupStyles(holder)
    }

    override fun getItemCount(): Int = items.size

    private fun setupElements(holder: ViewHolder, appItem: InstalledApp) {
        holder.appIcon.setImageDrawable(appItem.drawable)

        holder.appName.text = appItem.appName

        if (appItem.selected) {
            holder.appBackground.setBackgroundColor(context.resources.getColor(R.color.teal_200, context.theme))
            holder.appName.setBackgroundColor(context.resources.getColor(R.color.teal_200, context.theme))
        }
        else {
            val outValue = TypedValue()
            activity.theme.resolveAttribute(R.attr.ThemeName, outValue, true)

            if (outValue.string.equals("LightTheme")){
                holder.appBackground.setBackgroundColor(activity.resources.getColor(R.color.white, activity.theme))
                holder.appName.setBackgroundColor(activity.resources.getColor(R.color.white, activity.theme))
            } else{
                holder.appBackground.setBackgroundColor(activity.resources.getColor(R.color.black, activity.theme))
                holder.appName.setBackgroundColor(activity.resources.getColor(R.color.black, activity.theme))
            }
        }
    }

    private fun setupSelectingApp(holder: ViewHolder, appItem: InstalledApp, position: Int) {
        holder.itemView.setOnClickListener {
            if(appItem.selected) {
                selectedApps.remove(appItem.id)
            }
            else {
                selectedApps.add(appItem.id)
            }

            appItem.selected = !appItem.selected
            android.os.Handler(Looper.getMainLooper()).post {
                notifyItemChanged(position)
            }
        }
    }

    private fun setupStyles(holder: ViewHolder) {
        LayoutUtils.setHeight(holder.appCardView, 0.25f)
        LayoutUtils.setTextSize(holder.appName, 0.007f)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appCardView: CardView = view.findViewById(R.id.appContainer)
        val appBackground: View = view.findViewById(R.id.appContainerBg)
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val lang = context.resources.configuration.locales.get(0)
            val result = textToSpeech.setLanguage(lang)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.CANADA)
            }
        }
    }
}