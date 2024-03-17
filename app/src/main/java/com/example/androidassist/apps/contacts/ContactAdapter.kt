package com.example.androidassist.apps.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassist.R

class ContactAdapter(private val items: List<ContactInfo>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = items[position]
        holder.contactName.text = contact.name
        if(contact.image != null) {
            holder.contactImage.setImageBitmap(contact.image)
        }
        else {
            holder.contactImage.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactName: TextView = view.findViewById(R.id.tv_name)
        val contactImage: ImageView = view.findViewById(R.id.iv_profile)
    }
}