package com.example.androidassist

import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.LayoutUtils
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils

class AppsRecyclerViewAdapter(private val items: MutableList<InstalledApp>) : RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder>() {
    val context = AndroidAssistApplication.getAppContext()
    var selectedApps: MutableList<String> = SharedPreferenceUtils.getStringSetFromDefaultSharedPrefFile(
        context, "SelectedApps", setOf())?.toMutableList() ?: mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_app_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appItem = items[position]

        setupElements(holder, appItem)
        setupSelectingApp(holder, appItem, position)
        setupStyles(holder)
    }

    override fun getItemCount(): Int = items.size

    private fun setupElements(holder: ViewHolder, appItem: InstalledApp) {
        holder.appIcon.setImageDrawable(appItem.drawable)

        holder.appName.text = appItem.appName

        if (appItem.selected) {
            holder.appBackground.setBackgroundColor(context.resources.getColor(R.color.teal_200, context.theme))
        }
        else {
            holder.appBackground.setBackgroundColor(context.resources.getColor(R.color.white, context.theme))
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
}