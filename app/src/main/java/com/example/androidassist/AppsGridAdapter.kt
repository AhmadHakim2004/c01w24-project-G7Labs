package com.example.androidassist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.androidassist.sharedComponents.dataClasses.AppsInfo
import com.example.androidassist.sharedComponents.services.LayoutUtils
import kotlin.math.roundToInt

class AppsGridAdapter(private val context: Context, apps: List<AppsInfo>) : BaseAdapter() {
    private val items: List<AppsInfo> = apps

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): AppsInfo = items[position]

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.main_app_view, parent, false)

        val appCardView: CardView = view.findViewById(R.id.appContainer)
        val imageView: ImageView = view.findViewById(R.id.appIcon)
        val textView: TextView = view.findViewById(R.id.appName)

        val appItem: AppsInfo = getItem(position)

        // Set data to views
        imageView.setImageResource(appItem.imageResource)
        textView.text = appItem.appName

        setStyles(appCardView, textView)

        return view
    }

    private fun setStyles(cardView: CardView, textView: TextView) {
        val layoutUtils = LayoutUtils(context)

        // TODO() Add setting height inside LayoutUtilsService
        val newLayoutParams = cardView.layoutParams
        val wantedHeight = layoutUtils.height() * 0.25
        newLayoutParams.height = wantedHeight.roundToInt()
        cardView.layoutParams = newLayoutParams

        layoutUtils.setMargins(cardView, 0.1f)

        layoutUtils.setTextSize(textView, 0.007f)
    }
}