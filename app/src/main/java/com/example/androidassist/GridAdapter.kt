package com.example.androidassist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.androidassist.sharedComponents.dataClasses.ActionItem
import com.example.androidassist.sharedComponents.dataClasses.AdapterItem
import com.example.androidassist.sharedComponents.dataClasses.CustomApp
import com.example.androidassist.sharedComponents.dataClasses.InstalledApp
import com.example.androidassist.sharedComponents.utilities.LayoutUtils

class GridAdapter(private val context: Context, private val items: List<AdapterItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): AdapterItem = items[position]

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.main_app_view, parent, false)

        val itemsCardView: CardView = view.findViewById(R.id.appContainer)
        val imageView: ImageView = view.findViewById(R.id.appIcon)
        val textView: TextView = view.findViewById(R.id.appName)

        val item: AdapterItem = getItem(position)

        // Set data to views
        when (item) {
            is InstalledApp -> {
                imageView.setImageDrawable(item.drawable)
                textView.text = item.appName
            }
            is CustomApp,   -> {
                imageView.setImageResource(item.imageResource!!)
                textView.text = context.resources.getString(item.nameResource)
            }
            is ActionItem -> {
                imageView.setImageResource(item.imageResource!!)
                textView.text = context.resources.getString(item.nameResource)
            }
        }

        setStyles(itemsCardView, textView)

        return view
    }

    private fun setStyles(cardView: CardView, textView: TextView) {
        LayoutUtils.setHeight(cardView, 0.25f)

        LayoutUtils.setTextSize(textView, 0.007f)
    }
}